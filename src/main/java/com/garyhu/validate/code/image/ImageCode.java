package com.garyhu.validate.code.image;


import com.garyhu.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created on 2018/1/10.
 *
 * @author zlf
 * @since 1.0
 */
public class ImageCode extends ValidateCode {
    private static final long serialVersionUID = -3157935668300472492L;

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
