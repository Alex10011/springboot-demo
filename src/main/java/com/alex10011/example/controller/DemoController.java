package com.alex10011.example.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alex10011.example.bo.Bo_GetUserByName;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.crab2died.ExcelUtils;
import com.google.code.kaptcha.Producer;
import com.alex10011.example.annonation.MethodLock;
import com.alex10011.example.bo.Bo_GetUserByName;
import com.alex10011.example.domain.City;
import com.alex10011.example.domain.SysUser;
import com.alex10011.example.service.AsyncTaskTest;
import com.alex10011.example.service.CityService;
import com.alex10011.example.service.UserService;
import com.alex10011.example.util.ZipUtil;
import com.alex10011.example.vo.RspBean;
import com.alex10011.example.vo.TestExcel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

// @RequestBody  @ModelAttribute
@RestController
public class DemoController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(DemoController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private CityService cityService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private AsyncTaskTest asyncTask;
	@Autowired
	private Producer captchaProducer;

	@ApiOperation(value = "防重复", notes = "试试多个浏览器同时请求，只有第一个会被处理")
	@RequestMapping(value = "/methodLock", method = RequestMethod.POST)
	@MethodLock(key = "request.orderId")
	public RspBean<String> methodLock(@RequestParam(value = "orderId", required = true) String orderId) {
		try {
			// 延迟，模拟业务处理
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return success("this method is finish");
	}

	@ApiOperation(value = "打包下载", notes = "浏览器直接访问  http://127.0.0.1:8000/zipDownload ")
	@RequestMapping(value = "/zipDownload", method = RequestMethod.GET)
	public void zipDownload(HttpServletResponse response) {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"test.zip\"");
		response.setHeader("content-type", "application/octet-stream");

		// 读取源文件的目录
		File path = new File("d:\\test");
		File[] files = path.listFiles();
		try {
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			for (int i = 0; i < files.length; i++) {
				File file = files[i];

				// 演示创建子目录，demo每个文件归属一个子目录
				String subPath = i + "/";

				// 压缩文件
				ZipUtil.zipFile(subPath, file, zos);
			}
			zos.flush();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ApiOperation(value = "文件下载", notes = "浏览器直接访问  http://127.0.0.1:8000/oneDownload ")
	@RequestMapping(value = "/oneDownload", method = RequestMethod.GET)
	public void oneDownload(HttpServletResponse response) {
		String fullPath = "d://test.xlsx";
		File downloadFile = new File(fullPath);

		// set content attributes for the response
		response.setContentType("application/octet-stream");
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// Copy the stream to the response's output stream.
		try {
			InputStream myStream = new FileInputStream(fullPath);
			IOUtils.copy(myStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ApiOperation(value = "文件上传")
	@RequestMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=mutipart/form-data", method = RequestMethod.POST)
	public RspBean<String> upload(@ApiParam(value = "上传附件", required = true) MultipartFile file,
			HttpServletRequest request) {
		String fileKey = UUID.randomUUID().toString();
		// 获取后缀
		String fileName = file.getOriginalFilename();
		if (fileName.lastIndexOf(".") != -1) {
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			try {
				// 这里只是简单例子，文件直接输出到项目路径下。实际项目中，文件需要输出到指定位置，需要在增加代码处理。
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File("d:\\" + fileKey + "." + suffix)));
				out.write(file.getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success("");
	}

	@ApiOperation(value = "excel导入", notes = "使用Excel4J注释方式转对象")
	@RequestMapping(value = "/importExcel", method = RequestMethod.GET)
	public RspBean<String> importExcel() {
		String file = "D:\\test.xlsx";
		try {
			List<TestExcel> excel = ExcelUtils.getInstance().readExcel2Objects(file, TestExcel.class, 0, 0);

			for (TestExcel testExcel : excel) {
				System.out.println(testExcel);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return success("");
	}

	@ApiOperation(value = "异步方法", notes = "@Async的演示")
	@RequestMapping(value = "/testAsync", method = RequestMethod.GET)
	public RspBean<String> testAsync() {
		try {
			System.out.println("我开始执行了！");
			asyncTask.doSomething();
			System.out.println("我执行结束了！");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return success("finish");
	}

	@ApiOperation(value = "根据名字查用户", notes = "普通方式传参")
	@RequestMapping(value = "/getUserByName", method = RequestMethod.GET)
	// @AccessToken
	public RspBean<SysUser> getUserByName(@RequestParam(value = "name1", required = true) String name1,
			@RequestParam(value = "name2", defaultValue = "张三") String name2) {
		SysUser user = userService.getUserByName(name1);

		return success(user);
	}

	// 对象方式取参数, 演示了valid的使用 , @ModelAttribute用于展开对象里面的参数
	@ApiOperation(value = "根据xxx获取用户", notes = "采用对象传参，并使用valid校验参数对象，可携带分页参数")
	@RequestMapping(value = "/getUserByBo", method = RequestMethod.POST)
	public RspBean getUserByName_bo(@RequestParam(value = "name1", required = true) String name1,
                                    @ApiParam @RequestBody @Validated Bo_GetUserByName bo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			return failure(fieldError.getField() + ":" + fieldError.getDefaultMessage());
		}

		SysUser user = userService.getUserByName(bo.getUserName());

		return success(user);
	}

	@ApiOperation(value = "查询city1", notes = "restful风格接口")
	@RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
	public RspBean<City> findOneCity(@PathVariable("id") Long id) {
		City city = cityService.findCityById(id);

		return success(city);
	}

	@ApiOperation(value = "获取图片验证码", notes = "google的kaptcha，img标签src='captcha-image' ，访问 http://127.0.0.1:8000/captcha-image ")
	@RequestMapping(value = "/captcha-image", method = RequestMethod.GET)
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		String capText = captchaProducer.createText();
		System.out.println("capText: " + capText);

		try {
			String uuid = UUID.randomUUID().toString();
			stringRedisTemplate.opsForValue().set(uuid, capText, 60 * 5, TimeUnit.SECONDS);
			Cookie cookie = new Cookie("captchaCode", uuid);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

}
