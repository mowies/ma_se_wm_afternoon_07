/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geoschnitzel.treasurehunt;

import com.geoschnitzel.treasurehunt.map.MapContract;
import com.geoschnitzel.treasurehunt.map.MapPresenter;
import com.geoschnitzel.treasurehunt.model.WebService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MapPresenterTest {

    @Mock
    private MapContract.View mMapView;

    @Mock
    private WebService webService;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    private MapPresenter mMapPresenter;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mMapPresenter = new MapPresenter(mMapView, webService);

        // The presenter won't update the view unless it's active.
//        when(mMapView.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Then the presenter is set to the view
        verify(mMapView).setPresenter(mMapPresenter);
    }
}
