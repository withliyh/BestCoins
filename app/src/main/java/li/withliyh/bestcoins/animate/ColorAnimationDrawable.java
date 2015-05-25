package li.withliyh.bestcoins.animate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.AnimationUtils;

import java.util.Random;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ColorAnimationDrawable extends Drawable implements Animatable {

    private static final long FRAME_DURATION = 1000 / 60;
    private static final long ANIMATION_DURATION = 1500;

    private static final int ACCCENT_COLOR = 0x33FFFFFF;
    private static final int DIM_COLOR = 0x33000000;

    private static final Random mRandom = new Random();
    private final Paint mPaint = new Paint();

    private boolean mIsRunning;

    private int mStartColor;
    private int mEndColor;
    private int mCurrentColor;

    private long mStartTime;


    /**
     * Draw in its bounds (set via setBounds) respecting optional effects such
     * as alpha (set via setAlpha) and color filter (set via setColorFilter).
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();

        mPaint.setColor(mCurrentColor);
        canvas.drawRect(bounds, mPaint);

        mPaint.setColor(ACCCENT_COLOR);
        canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.top + 1, mPaint);

        mPaint.setColor(DIM_COLOR);
        canvas.drawRect(bounds.left, bounds.bottom - 2, bounds.right, bounds.bottom, mPaint);
    }

    /**
     * Specify an alpha value for the drawable. 0 means fully transparent, and
     * 255 means fully opaque.
     *
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {
        oops("setAlpha(int)");
    }

    /**
     * Specify an optional color filter for the drawable. Pass {@code null} to
     * remove any existing color filter.
     *
     * @param cf the color filter to apply, or {@code null} to remove the
     *           existing color filter
     */
    @Override
    public void setColorFilter(ColorFilter cf) {
        oops("setColorFilter(ColorFilter)");
    }

    /**
     * Return the opacity/transparency of this Drawable.  The returned value is
     * one of the abstract format constants in
     * {@link PixelFormat}:
     * {@link PixelFormat#UNKNOWN},
     * {@link PixelFormat#TRANSLUCENT},
     * {@link PixelFormat#TRANSPARENT}, or
     * {@link PixelFormat#OPAQUE}.
     * <p/>
     * <p>Generally a Drawable should be as conservative as possible with the
     * value it returns.  For example, if it contains multiple child drawables
     * and only shows one of them at a time, if only one of the children is
     * TRANSLUCENT and the others are OPAQUE then TRANSLUCENT should be
     * returned.  You can use the method {@link #resolveOpacity} to perform a
     * standard reduction of two opacities to the appropriate single output.
     * <p/>
     * <p>Note that the returned value does <em>not</em> take into account a
     * custom alpha or color filter that has been applied by the client through
     * the {@link #setAlpha} or {@link #setColorFilter} methods.
     *
     * @return int The opacity class of the Drawable.
     * @see PixelFormat
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }


    /**
     * Starts the drawable's animation.
     */
    @Override
    public void start() {
        if (!isRunning()) {
            mIsRunning = true;

            mStartTime = AnimationUtils.currentAnimationTimeMillis();
            mStartColor = randomColor();
            mEndColor = randomColor();

            scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
        }
    }

    /**
     * Stops the drawable's animation.
     */
    @Override
    public void stop() {
        if (isRunning()) {
            unscheduleSelf(mUpdater);
            mIsRunning = false;
        }
    }

    /**
     * Indicates whether the animation is running.
     *
     * @return True if the animation is running, false otherwise.
     */
    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    private void oops(String message) {
        throw new UnsupportedOperationException("ColorAnimationDrawable doesn't support " + message);
    }

    private static int randomColor() {
        return mRandom.nextInt() & 0x00FFFFFF;
    }

    private static int evaluate(float fraction, int startValue, int endValue) {
        return (int) (startValue + fraction * (endValue - startValue));
    }

    private final Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            long duration = now - mStartTime;
            if (duration >= ANIMATION_DURATION) {
                mStartColor = mEndColor;
                mEndColor = randomColor();
                mStartTime = now;
                mCurrentColor = mStartColor;
            } else {
                float fraction = duration / (float) ANIMATION_DURATION;
                mCurrentColor = Color.rgb(
                        evaluate(fraction, Color.red(mStartColor), Color.red(mEndColor)),
                        evaluate(fraction, Color.green(mStartColor), Color.green(mEndColor)),
                        evaluate(fraction, Color.blue(mStartColor), Color.blue(mEndColor)));
            }
            scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
            invalidateSelf();
        }
    };
}
