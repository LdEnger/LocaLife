package main.java.com.hiveview.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.vo.CityVo;
import com.hiveview.entity.vo.ProvinceVo;
import com.hiveview.service.AreasService;

@Controller
@RequestMapping("/areas")
public class AreasAction {

	@Autowired
	AreasService areasService;
	
	@RequestMapping(value="/getProvinceList")
	@ResponseBody
	public List<ProvinceVo> getProvinceList(){
		return areasService.getProvinceAll();
	}
	
	@RequestMapping(value="/getCityList")
	@ResponseBody
	public List<CityVo> getCityList(ProvinceVo provinceVo){
		return areasService.getCityByProvinceCode(provinceVo.getProvinceCode());
	}
	
}
