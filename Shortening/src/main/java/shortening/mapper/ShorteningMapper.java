package shortening.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import shortening.model.Shortening;

@Mapper
public interface ShorteningMapper {
	public int insertShort(Map<String, Object> map);
	
	public String alreadyShort(Map<String, Object> map);
	
	public int updateRequestCount(Map<String, Object> map);
	
	public int isDuplicateKey(Map<String, Object> map);
	
	public int updateRedirectCount(Map<String, Object> map);

	public String getShortUrl(Map<String, Object> map);

	public Shortening getHistory(Map<String, Object> reqMap);
}