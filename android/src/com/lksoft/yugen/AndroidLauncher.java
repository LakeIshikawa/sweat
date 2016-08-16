package com.lksoft.yugen;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new YugenGame("stages/lionking/lionking.stg", "chars/valkyrie/valkyrie.def", "chars/valkyrie/valkyrie.def"), config);
	}
}
