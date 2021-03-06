package com.chinasofti.mall.user.service;

import java.util.List;

import com.chinasofti.mall.common.entity.PtRole;
import com.chinasofti.mall.common.entity.Tree;

import net.sf.json.JSONObject;

public interface PtRoleService {

	int save(PtRole ptRole);

	PtRole findById(String ids);
	
	int deleteByPrimaryKey(String ids);

	String findAll();
	
	JSONObject selectByExample(PtRole ptRole);

	int update(PtRole ptRole);

	List<Tree> menuTree(String roleId);

	List<Tree> showTree(String id);

	int saveIds(PtRole ptRole);

	int saveMenuIds(PtRole ptRole);

}
