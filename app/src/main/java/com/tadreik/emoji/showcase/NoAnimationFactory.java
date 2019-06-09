package com.tadreik.emoji.showcase;

import android.graphics.Point;
import android.view.View;
import com.tadreik.emoji.showcase.AnimationFactory;
import com.tadreik.emoji.showcase.ShowcaseView;

class NoAnimationFactory implements AnimationFactory {

    @Override
    public void fadeInView(View target, long duration, AnimationFactory.AnimationStartListener listener) {
        listener.onAnimationStart();
    }

    @Override
    public void fadeOutView(View target, long duration, AnimationEndListener listener) {
        listener.onAnimationEnd();
    }

    @Override
    public void animateTargetToPoint(ShowcaseView showcaseView, Point point) {
        showcaseView.setShowcasePosition(point.x, point.y);
    }
}
