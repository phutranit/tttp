package vn.greenglobal.tttp.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TiepCongDanController {
	private static Log log = LogFactory.getLog(TiepCongDanController.class);
	
	@RequestMapping(method = RequestMethod.GET, value = "/danhsachvuviecs", produces = { "application/json" })
	public @ResponseBody JSONObject getAllDanTocs() throws FileNotFoundException, IOException, ParseException {
		ClassLoader classLoader = getClass().getClassLoader();
		String tcdFile = classLoader.getResource("don.json").getFile();
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(new FileInputStream(tcdFile), "UTF-8");
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
		log.info("json " + jsonObject.toJSONString());
		return jsonObject;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/nguoidungdons", produces = { "application/json" })
	public @ResponseBody JSONObject getAllNguoiDungDons() throws FileNotFoundException, IOException, ParseException {
		ClassLoader classLoader = getClass().getClassLoader();
		String tcdFile = classLoader.getResource("ndd.json").getFile();
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(new FileInputStream(tcdFile), "UTF-8");
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
		log.info("json " + jsonObject.toJSONString());
		return jsonObject;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/nguoidungdons/{id}")
	public @ResponseBody JSONObject getNguoiDungDon(@PathVariable("id") long id) throws IOException, ParseException {
		log.info("Unable to getNguoiDungDon with id="+id+", not found");
		ClassLoader classLoader = getClass().getClassLoader();
		String tcdFile = classLoader.getResource("ndd.json").getFile();
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(new FileInputStream(tcdFile), "UTF-8");
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
		log.info("json " + jsonObject.toJSONString());
		return jsonObject;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/congdans")
	public @ResponseBody JSONObject getCongDans() throws IOException, ParseException {
		log.info("Unable to getCongDans");
		ClassLoader classLoader = getClass().getClassLoader();
		String tcdFile = classLoader.getResource("congdan.json").getFile();
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(new FileInputStream(tcdFile), "UTF-8");
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
		log.info("json " + jsonObject.toJSONString());
		return jsonObject;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/congdans/{id}")
	public @ResponseBody JSONObject getCongDans(@PathVariable("id") long id) throws IOException, ParseException {
		log.info("Unable to getCongDans with id="+id+", not found");
		ClassLoader classLoader = getClass().getClassLoader();
		String tcdFile = classLoader.getResource("congdan.json").getFile();
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(new FileInputStream(tcdFile), "UTF-8");
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
		log.info("json " + jsonObject.toJSONString());
		return jsonObject;
	}
}
