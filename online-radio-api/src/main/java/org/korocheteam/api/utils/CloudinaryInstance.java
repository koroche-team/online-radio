package org.korocheteam.api.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class CloudinaryInstance {
	@Getter
	private static Cloudinary cloudinary;

	public CloudinaryInstance() {
		if (cloudinary == null) {
			cloudinary = new Cloudinary(ObjectUtils.asMap(
					"cloud_name", "de5binygw",
					"api_key", "967433251694596",
					"api_secret", "DmISE6bttvpM-ZV3TaBWsTYaRIY"));
		}
	}

}