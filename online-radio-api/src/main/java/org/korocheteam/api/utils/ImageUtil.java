package org.korocheteam.api.utils;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class ImageUtil {

	public String createUrl(File pic) {
		String url;

		try {
			Map upload = CloudinaryInstance.getCloudinary().uploader()
					.upload(pic, ObjectUtils.asMap("public_id", pic.getName()));
			url = (String) upload.get("url");
			pic.delete();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		return url;
	}
}
