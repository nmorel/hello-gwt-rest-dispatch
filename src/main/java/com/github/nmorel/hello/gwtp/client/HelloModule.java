/*
 * Copyright 2014 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.hello.gwtp.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.rest.client.ActionMetadataProvider;
import com.gwtplatform.dispatch.rest.client.RestApplicationPath;
import com.gwtplatform.dispatch.rest.client.gin.RestDispatchAsyncModule;
import com.gwtplatform.dispatch.rest.client.serialization.JacksonMapperProvider;

/**
 * @author Nicolas Morel.
 */
public class HelloModule extends AbstractGinModule {

    @Override
    protected void configure() {
        RestDispatchAsyncModule.Builder dispatchBuilder = new RestDispatchAsyncModule.Builder();
        install( dispatchBuilder.build() );

        bindConstant().annotatedWith( RestApplicationPath.class ).to( "/api" );
    }
}

