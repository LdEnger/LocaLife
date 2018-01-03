package main.java.com.hiveview.action.award;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hiveview.entity.bo.Data;

@Controller
@RequestMapping("/awardDetail")
public class AwardDetailAction {

	
	@RequestMapping(value="/add")
	public Data add(){
		Data data = new Data();
		return data;
	}
}
