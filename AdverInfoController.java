package com.sxhy.advertise.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxhy.advertise.domain.AdverInfoDO;
import com.sxhy.advertise.service.AdverInfoService;
import com.sxhy.common.domain.DataResult;
import com.sxhy.common.exception.BDException;
import com.sxhy.common.utils.PageUtils;
import com.sxhy.common.utils.Query;
import com.sxhy.common.utils.R;

/**
 * 
 * 
 * @author xsn
 * @email 1992lcg@163.com
 * @date 2019-05-09 10:29:34
 */
 
@Controller
@RequestMapping("/advertise/adverInfo")
public class AdverInfoController {
	@Autowired
	private AdverInfoService adverInfoService;
	
	@GetMapping()
	@RequiresPermissions("advertise:adverInfo:adverInfo")
	String AdverInfo(){
	    return "advertise/adverInfo/adverInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("advertise:adverInfo:adverInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AdverInfoDO> adverInfoList = adverInfoService.list(query);
		int total = adverInfoService.count(query);
		PageUtils pageUtils = new PageUtils(adverInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("advertise:adverInfo:add")
	String add(){
	    return "advertise/adverInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("advertise:adverInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		AdverInfoDO adverInfo = adverInfoService.get(id);
		model.addAttribute("adverInfo", adverInfo);
	    return "advertise/adverInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("advertise:adverInfo:add")
	public R save( AdverInfoDO adverInfo){
		if(adverInfoService.save(adverInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("advertise:adverInfo:edit")
	public R update( AdverInfoDO adverInfo){
		adverInfoService.update(adverInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("advertise:adverInfo:remove")
	public R remove( Integer id){
		if(adverInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("advertise:adverInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		adverInfoService.batchRemove(ids);
		return R.ok();
	}
	
	//根据传来的类型查询出其中一个
	@GetMapping("/selectoneadver")
	@ResponseBody
	public DataResult<AdverInfoDO> selectone(@RequestParam Integer advType){
		AdverInfoDO adver=adverInfoService.selectone(advType);
		System.out.println(adver);
		DataResult<AdverInfoDO> data=new DataResult<>();
		data.setData(adver);	
		return data;
	}
}
