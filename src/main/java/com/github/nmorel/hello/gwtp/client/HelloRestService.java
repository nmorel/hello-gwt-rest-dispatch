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

import com.github.nmorel.hello.gwtp.shared.GreetingRequest;
import com.github.nmorel.hello.gwtp.shared.GreetingResponse;
import com.gwtplatform.dispatch.rest.shared.RestAction;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Nicolas Morel.
 */
@Path("hello")
public interface HelloRestService {

    @POST
    RestAction<GreetingResponse> greet(GreetingRequest request);

}

