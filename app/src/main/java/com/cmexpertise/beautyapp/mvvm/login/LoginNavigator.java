/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version stra2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.cmexpertise.beautyapp.mvvm.login;

import com.cmexpertise.beautyapp.model.ResponseBase;
import com.cmexpertise.beautyapp.model.loginModel.LoginResponse;

/**
 * Created by kailash patel
 */

public interface LoginNavigator
{

    void handleError(Throwable throwable);
    void login();
    void skip();
    void register();
    void forgotPassword();
    void fbLogin();
    void googleLogin();

    void  loginResponce(LoginResponse userResponse);
    void  addDeviceResponce(ResponseBase userResponse);

}
