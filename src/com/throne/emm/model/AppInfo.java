package com.throne.emm.model;

import java.io.Serializable;
public class AppInfo  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 应用程序具体参数
	 * 参数
	 */
	private int key;					//应用大小
	private String packageType; 	 	//应用类型
	private String appName;  	//应用名称
	private String packageName;	//应用程序唯一标示
	private String packageVersion;	//应用版本号
	private String packageSize;		//应用大小
	private String packageDesc;    	//应用描述
	private String downloadcount;	//下载总数
	private String pkgFilePath;		//应用文件路径   下载链接
	private String pkgHeadpicPath;	//应用图标路径   下载链接
	private String DisplayName;		//DisplayName
	private String bundlename;		//BundleName
	private String background;      //BundleName
	private String updateTime;
	private String isUpdate;
	private String isDelete;
	private String id;
	/*
	 * 方法
	 */
	

	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public String getPackageType() {
		return packageType;
	}


	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageVersion() {
		return packageVersion;
	}


	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}


	public String getPackageSize() {
		return packageSize;
	}


	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}


	public String getPackageDesc() {
		return packageDesc;
	}


	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}


	public String getDownloadcount() {
		return downloadcount;
	}


	public void setDownloadcount(String downloadcount) {
		this.downloadcount = downloadcount;
	}


	public String getPkgFilePath() {
		return pkgFilePath;
	}


	public void setPkgFilePath(String pkgFilePath) {
		this.pkgFilePath = pkgFilePath;
	}


	public String getPkgHeadpicPath() {
		return pkgHeadpicPath;
	}


	public void setPkgHeadpicPath(String pkgHeadpicPath) {
		this.pkgHeadpicPath = pkgHeadpicPath;
	}


	public String getDisplayName() {
		return DisplayName;
	}


	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}


	public String getBundlename() {
		return bundlename;
	}


	public void setBundlename(String bundlename) {
		this.bundlename = bundlename;
	}

	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


    public String getBackground() {
        return background;
    }


    public void setBackground(String background) {
        this.background = background;
    }


    public String getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public String getIsUpdate() {
        return isUpdate;
    }


    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }


    public String getIsDelete() {
        return isDelete;
    }


    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
	


}
