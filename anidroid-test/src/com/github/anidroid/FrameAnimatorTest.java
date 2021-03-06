/*
 *   Copyright 2011, Dmitry Stropaloff <dmitry.stropaloff@gmail.com>
 *
 *   This file is part of AniDroid project.
 *
 *   AniDroid is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as
 *   published by the Free Software Foundation, either version 3 of
 *   the License, or (at your option) any later version.
 *
 *   AniDroid is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with AniDroid.  If not, see
 *   <http://www.gnu.org/licenses/>.
 */

package com.github.anidroid;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.test.InstrumentationTestCase;

public class FrameAnimatorTest extends InstrumentationTestCase {
	Context context;
	TestStub stubs;
	FrameAnimator animator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		context = getInstrumentation().getContext();
		stubs = new TestStub();

		animator = FrameAnimator.getInstance(context);
	}

	public void testGetInstance_ContextInitialized() {
		assertEquals(context,
				FrameAnimator.getInstance(context).context);
	}

	public void testGetInstance_FrameUtilInitialized() {
		assertNotNull(FrameAnimator.getInstance(context).frameUtil);
	}

	public void testGetInstance_AssetUtilInitialized() {
		assertNotNull(FrameAnimator.getInstance(context).assetUtil);
	}

	public void testCreate_FrameCount() {
		final AnimationDrawable animation = animator.create("ajax-loader", 250, false);

		assertEquals(4, animation.getNumberOfFrames());
	}

	public void testCreate_Duration() {
		final AnimationDrawable animation = animator.create("ajax-loader", 350, false);

		assertEquals(350, animation.getDuration(0));
	}

	public void testCreate_OneShot() {
		final AnimationDrawable animation = animator.create("ajax-loader", 150, true);

		assertTrue(animation.isOneShot());
	}

	public void testCreate_EmptyFileList() {
		animator.assetUtil = stubs.assetUtilWithEmptyFilesList();

		try {
			animator.create("ajax-loader", 150, true);
			fail();
		} catch (final AniDroidException e) { }
	}

	public void testCreate_FrameFileOpenFailed() {
		animator.assetUtil = stubs.assetUtilExceptionOnOpen(context.getAssets());

		try {
			animator.create("ajax-loader", 150, true);
			fail();
		} catch (final AniDroidException e) { }
	}
}
