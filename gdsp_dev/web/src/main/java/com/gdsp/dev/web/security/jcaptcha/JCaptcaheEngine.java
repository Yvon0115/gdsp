package com.gdsp.dev.web.security.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByBufferedImageOp;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.OverlapGlyphsUsingShapeVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateGlyphsVerticalRandomVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 验证码生成引擎
 *
 * @author paul.yang
 * @version 1.0 15-2-6
 * @since 1.6
 */
public class JCaptcaheEngine extends ImageCaptchaEngine {

    /**
     * 验证码个数
     */
    public int wordLength = 4;

    /**
     * 验证码引擎构造方法
     */
    public JCaptcaheEngine() {
        wordLength = 4;
        buildInitialFactories();
        checkFactoriesSize();
    }

    /**
     * 构造方法
     * @param wordLength 验证码长度
     */
    public JCaptcaheEngine(int wordLength) {
        this.wordLength = wordLength;
        buildInitialFactories();
        checkFactoriesSize();
    }

    /**
     * Add a factory to the gimpy list
     *
     * @return true if added false otherwise
     */
    @SuppressWarnings("unchecked")
	public boolean addFactory(ImageCaptchaFactory factory) {
        return factory != null && this.factories.add(factory);
    }

    /**
     * Add an array of factories to the gimpy list
     */
    @SuppressWarnings("unchecked")
	public void addFactories(ImageCaptchaFactory[] factories) {
        checkNotNullOrEmpty(factories);
        this.factories.addAll(Arrays.asList(factories));
    }

    private void checkFactoriesSize() {
        if (factories.size() == 0)
            throw new CaptchaException(
                    "This gimpy has no factories. Please initialize it "
                            + "properly with the buildInitialFactory() called by "
                            + "the constructor or the addFactory() mehtod later!");
    }

    /**
     * this method should be implemented as folow :
     * <ul>
     * <li>First construct all the factories you want to initialize the gimpy
     * with</li>
     * <li>then call the this.addFactoriy method for each factory</li>
     * </ul>
     */
    protected void buildInitialFactories() {
        //word generator
        WordGenerator dictionnaryWords = new RandomWordGenerator("abcdefghijklmnopqrstuvwxyz123456789");
        //new ComposeDictionaryWordGenerator(new com.octo.captcha.component.word.FileDictionary("toddlist"));
        //wordtoimage components
        TextPaster randomPaster = new GlyphsPaster(wordLength, wordLength, new RandomListColorGenerator(
                new Color[] { //
                        new Color(31, 129, 220), //
                        new Color(219, 82, 125), //
                        new Color(220, 0, 85) //
                }),
                new GlyphsVisitors[] { //
                        new TranslateGlyphsVerticalRandomVisitor(1), //
                        // new RotateGlyphsRandomVisitor(Math.PI/32),
                        new OverlapGlyphsUsingShapeVisitor(3), //
                        new TranslateAllToRandomPointVisitor() //
                });
        /*
         * new TextVisitor[]{ new OverlapGlyphsTextVisitor(6) }, null
         */
        BackgroundGenerator back = new UniColorBackgroundGenerator(200, 70, new Color(255,244,207));
        //new UniColorBackgroundGenerator(200, 70, Color.white);
        //FunkyBackgroundGenerator(200, 70)
        FontGenerator shearedFont = new RandomFontGenerator(60, 60, //
                new Font[] { new Font("nyala", Font.BOLD, 60), //
                        new Font("Bell MT", Font.PLAIN, 60), //
                        new Font("Credit valley", Font.BOLD, 60) //
                }, false);
        PinchFilter pinch = new PinchFilter();
        pinch.setAmount(-.5f);
        pinch.setRadius(30);
        pinch.setAngle((float) (Math.PI / 16));
        pinch.setCentreX(0.5f);
        pinch.setCentreY(-0.01f);
        pinch.setEdgeAction(ImageFunction2D.CLAMP);
        PinchFilter pinch2 = new PinchFilter();
        pinch2.setAmount(-.6f);
        pinch2.setRadius(70);
        pinch2.setAngle((float) (Math.PI / 16));
        pinch2.setCentreX(0.3f);
        pinch2.setCentreY(1.01f);
        pinch2.setEdgeAction(ImageFunction2D.CLAMP);
        PinchFilter pinch3 = new PinchFilter();
        pinch3.setAmount(-.6f);
        pinch3.setRadius(70);
        pinch3.setAngle((float) (Math.PI / 16));
        pinch3.setCentreX(0.8f);
        pinch3.setCentreY(-0.01f);
        pinch3.setEdgeAction(ImageFunction2D.CLAMP);
        java.util.List<ImageDeformation> textDef = new ArrayList<ImageDeformation>();
        textDef.add(new ImageDeformationByBufferedImageOp(pinch));
        textDef.add(new ImageDeformationByBufferedImageOp(pinch2));
        textDef.add(new ImageDeformationByBufferedImageOp(pinch3));
        //word2image 1
        WordToImage word2image = new DeformedComposedWordToImage(false, shearedFont, back, randomPaster, new ArrayList<ImageDeformation>(), new ArrayList<ImageDeformation>(), textDef);
        this.addFactory(new GimpyFactory(dictionnaryWords, word2image, false));
    }
}
