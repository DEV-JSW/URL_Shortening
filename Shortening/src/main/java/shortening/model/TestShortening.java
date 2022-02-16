package shortening.model;

public class TestShortening {
	private String siteUrl;
	private String shortKey;
	private long shortCnt;
	private long redirectCnt;
	
	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getShortKey() {
		return shortKey;
	}

	public void setShortKey(String shortKey) {
		this.shortKey = shortKey;
	}

	public long getShortCnt() {
		return shortCnt;
	}

	public void setShortCnt(long shortCnt) {
		this.shortCnt = shortCnt;
	}

	public long getRedirectCnt() {
		return redirectCnt;
	}

	public void setRedirectCnt(long redirectCnt) {
		this.redirectCnt = redirectCnt;
	}
}