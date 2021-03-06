package com.ma.cm.entity;

import java.io.Serializable;

public class Content implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3259706042043140849L;

	private long productId;
	
	private long contentId;
	
	private String name;
	
	private String poster;
	
	private String icon;
	
	private String screenShot;
	
	private int type;
	
	private String link;
	
	private String tip;
	
	private String options;
	
	public Content() {
		
	}
	
	public Content(long productId, long contentId, String name, String poster, String icon, String screenShot, int type, String link, String tip, String options) {
		this.productId = productId;
		this.contentId = contentId;
		this.name = name;
		this.poster = poster;
		this.icon = icon;
		this.screenShot = screenShot;
		this.type = type;
		this.link = link;
		this.tip = tip;
		this.options = options;
	}
	
	public Content(long productId, String name, String poster, String icon, String screenShot, int type, String link, String tip, String options) {
		this.productId = productId;
		this.poster = poster;
		this.icon = icon;
		this.screenShot = screenShot;
		this.type = type;
		this.link = link;
		this.tip = tip;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getScreenShot() {
		return screenShot;
	}

	public void setScreenShot(String screenShot) {
		this.screenShot = screenShot;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
