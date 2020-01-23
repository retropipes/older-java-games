package com.puttysoftware.fantastlereboot.objects.temporary;

import com.puttysoftware.fantastlereboot.assets.ObjectImageIndex;
import com.puttysoftware.fantastlereboot.objectmodel.ColorShaders;
import com.puttysoftware.fantastlereboot.objectmodel.FantastleObject;

class ShadowArrowNorthwest extends FantastleObject {
  // Constructors
  public ShadowArrowNorthwest() {
    super(-1, "arrow_northwest", ObjectImageIndex.ARROW_NORTHWEST,
        ColorShaders.shadow());
  }
}
