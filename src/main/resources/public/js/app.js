var app = angular.module('cuishou', ['ngRoute']);

app.config(function ($provide) {
    //重写ng-click 避免重复点击事件
    $provide.decorator('ngClickDirective', ['$delegate', '$timeout', function ($delegate, $timeout) {
        //装饰器decorator可为angularjs的service添加功能
        //第一个参数：需要装饰的Service名；第二个参数：一个接受$delegate的回调函数，$delegate代表我们的原来的service实例。
        var original = $delegate[0].compile;
        var delay = 500;
        $delegate[0].compile = function (element, attrs, transclude) {
            var disabled = false;
            function onClick(evt) {
                if (disabled) {
                    //取消事件
                    evt.preventDefault();
                    //阻止该元素绑定的后序相同类型事件的监听函数的执行
                    evt.stopImmediatePropagation();
                } else {
                    disabled = true;
                    //定时器
                    $timeout(function () {
                        disabled = false;
                    }, delay, false);
                }
            }
            element.on('click', onClick);
            return original(element, attrs, transclude);
        };
        return $delegate;
    }]);
    // 全局参数设置
    $provide.provider('globalParam', function () {
        this.$get = function () {
            var globalParam = {};

            globalParam['basePath'] = '/apollo/csplatform/web';
            globalParam['htmlStr'] = '';

            if (window.location.href.indexOf('/login') < 0) {
                $.ajax({
                    type: 'post',
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    url: '/apollo/csplatform/user/componentList',
                    async: false,
                    crossDomain: true,
                    success: function (data) {
                        if (data.code == 9055){
                            alert(data.desc);
                            window.location.href = '/apollo/csplatform/newViews/login.html';
                        }
                        globalParam['ctrl'] = data.detailInfo;
                    }
                })
            }

            return globalParam;
        }
    })
    // 验证
    $provide.provider('validate', function () {
        this.$get = function () {
            var validate = {};

            // null/undefined 验证
            validate.isNull = function (obj) {
                if (obj == null || obj == undefined) {
                    return true;
                } else {
                    return false;
                }
            }
            // 空值 验证
            validate.isEmpty = function (str) {
                if (str == null || str == undefined || str == '') {
                    return true;
                } else {
                    return false;
                }
            }
            // 手机号码 验证
            validate.isMobile = function (mobile) {
                var mobileReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (mobileReg.test(mobile)) {
                    return true;
                } else {
                    return false;
                }
            }
            // 密码复杂性 验证
            validate.isPassword = function (password) {
                var passwordReg1 = /^.*(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).*$/;
                var passwordReg2 = /^.{8,16}$/;
                if (passwordReg1.test(password)) {
                    if (passwordReg2.test(password)) {
                        var passwordArr = password.split(''), count = 0;
                        for (var i = 0; i < passwordArr.length - 1; i++) {
                            if (!isNaN(passwordArr[i]) && !isNaN(passwordArr[i + 1])) {
                                if (passwordArr[i + 1] - passwordArr[i] == 1) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            } else {
                                count = 0;
                            }
                            if (count == 4) {
                                return 3;// 密码不能出现连续5位递增数字
                            }
                        }
                        count = 0;
                        for (var i = 0; i < passwordArr.length - 1; i++) {
                            if (!isNaN(passwordArr[i]) && !isNaN(passwordArr[i + 1])) {
                                if (passwordArr[i + 1] - passwordArr[i] == -1) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            } else {
                                count = 0;
                            }
                            if (count == 4) {
                                return 4;// 密码不能出现连续5位递减数字
                            }
                        }
                        return 0;// 验证通过
                    } else {
                        return 2;// 密码8到16位
                    }
                } else {
                    return 1;// 密码至少包含1个数字，1个小写字母，1个大写字母
                }
            }

            return validate;
        }
    })

    // ajax请求封装
    $provide.provider('ajax', function () {
        this.$get = function () {
            var ajax = {};

            ajax.http = function (http, box, url, data, success, method, errorType) {
                http({
                    method: !!method ? method.toUpperCase() : 'POST',
                    url:  url,
                    data: data,
                    timeout: 30000,
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                }).then(function successCallback(response) {
                    // box.removeLoading();
                    // if (errorType == 'caseSearch') {
                    //     box.removeTableLoading('caseSearchTable');
                    // }
                    // var data = response.data;
                    // if (data.code == 0 || data.code == 8897) {
                    //     success(data);
                    // } else if (data.code == 9009 || data.code == 9023) {
                    //     alert(data.desc);
                    //     sessionStorage.removeItem('userId');
                    //     sessionStorage.removeItem('workerId');
                    //     sessionStorage.removeItem('userName');
                    //     sessionStorage.removeItem('firstUserId');
                    //     sessionStorage.removeItem('sysPermission');
                    //     sessionStorage.removeItem('defaultCaseId');
                    //     sessionStorage.removeItem('defaultImportCaseChannel');
                    //     sessionStorage.removeItem('caseId');
                    //     sessionStorage.removeItem('importCaseChannel');
                    //     sessionStorage.removeItem('csSearchParams');
                    //     sessionStorage.removeItem('menuChangeType');
                    //     sessionStorage.removeItem('sidebarBaseParams');
                    //     sessionStorage.removeItem('sidebarType');
                    //     if (window.location.href.indexOf('/login') > 0) {
                    //         $('#loginUserName').val('');
                    //         $('#loginPassword').val('');
                    //     } else {
                    //         window.location.href = '/apollo/csplatform/newViews/login.html';
                    //     }
                    // } else {
                    //     box.alert('cs-mask-text', data.desc);
                    //     if (!!errorType && errorType === 'caseImportFn') {
                    //         box.removeTableLoading('caseSearchTable');
                    //     }
                    // }
                }, function errorCallback(response) {
                    // box.removeLoading();
                    // if (!!errorType && errorType === 'caseChange') {
                    //     box.alert('cs-mask-text', '转派进行中，本批案件请勿再次操作！');
                    // } else if (!!errorType && errorType === 'intoCaseByPerson') {
                    //     box.alert('cs-mask-text', '手动进件进行中，本批案件请勿再次操作！');
                    // } else {
                    //     box.alert('cs-mask-text', '请求失败！');
                    // }
                })
            }

            return ajax;
        }
    })

    // 分页栏
    $provide.provider('pagination', function () {
        this.$get = function () {
            var pagination = {};
            pagination.create = function (id, pageIndex, pageSize, totalCount, showPageSizeFlag, clickFn) {// id: 标签id;pageIndex: 当前页数;pageSize: 每页展示数;totalCount: 查询的总记录数;clickFn: 按钮点击事件
                // totalCount==0
                if (totalCount == 0) {
                    $('#' + id).empty();
                    return;
                }
                pageIndex = parseInt(pageIndex), pageSize = parseInt(pageSize), totalCount = parseInt(totalCount);
                var pageCount = totalCount % pageSize === 0 ? totalCount / pageSize : parseInt(totalCount / pageSize) + 1;// 计算总页数
                var paginationStr = '<ul class="clearfix">', ellipsisStr = '<li class="cs-pagination-ellipsis">...</li>';
                var goPageButton='<div class="go_page_condition"><span>第</span><input type="text"/><span>页</span>' +
                    '<li id="cs-pagination-last-flag" style="width:40px;margin-top: 5px">GO</li></div>';

                var selectPageSize = "<div class='clickPageSize'><span>展示条数</span>"+
                    "<li id='pageSize15'>15</li>" +
                    "<li id='pageSize50'>50</li>" +
                    "<li id='pageSize100'>100</li>" +
                    "<li id='pageSize200'>200</li>" +
                    "<li id='pageSize500'>500</li></div>";

                if (pageIndex != 1) {// 当前页是首页，上一页按钮隐藏
                    paginationStr += '<li class="cs-pagination-first" data-index="' + (pageIndex - 1) + '">上一页</li>'
                }
                if (pageCount <= 5) {// 总页数小于5，全部展示
                    for (var i = 1; i < pageCount + 1; i++) {
                        paginationStr += '<li data-index="' + i + '"' + (i == pageIndex ? ' class="cs-pagination-checked"' : '') + '>' + i + '</li>';
                    }
                } else {
                    if (pageIndex < 4) {
                        for (var i = 1; i < 4; i++) {
                            paginationStr += '<li data-index="' + i + '"' + (i == pageIndex ? ' class="cs-pagination-checked"' : '') + '>' + i + '</li>';
                        }
                        paginationStr += ellipsisStr + '<li data-index="' + pageCount + '">' + pageCount + '</li>';
                    } else if (pageIndex > pageCount - 3) {
                        paginationStr += '<li data-index="1">1</li>' + ellipsisStr;
                        for (var i = pageCount - 2; i <= pageCount; i++) {
                            paginationStr += '<li data-index="' + i + '"' + (i == pageIndex ? ' class="cs-pagination-checked"' : '') + '>' + i + '</li>';
                        }
                    } else {
                        paginationStr += '<li data-index="1">1</li>' + ellipsisStr;
                        for (var i = pageIndex - 1; i <= pageIndex + 1; i++) {
                            paginationStr += '<li data-index="' + i + '"' + (i == pageIndex ? ' class="cs-pagination-checked"' : '') + '>' + i + '</li>';
                    }
                        paginationStr += ellipsisStr + '<li data-index="' + pageCount + '">' + pageCount + '</li>';
                    }
                }
                if (pageIndex != pageCount) {// 当前页是末页，下一页按钮隐藏
                    paginationStr += '<li class="cs-pagination-last" data-index="' + (pageIndex + 1) + '">下一页</li>'
                }
                if (pageCount > 5){
                    paginationStr += goPageButton;
                }
                if(showPageSizeFlag == true){
                    paginationStr += selectPageSize;
                }
                paginationStr += '</ul>';
                $('#' + id).empty().append(paginationStr);
                $('#' + id).find('ul  li').unbind('click').click(function () {
                    if ($(this).hasClass('cs-pagination-checked') || $(this).hasClass('cs-pagination-ellipsis')) {
                        return;
                    }
                    var pageNo = $(this).attr('data-index');
                    clickFn(pageNo,pageSize);
                })

                $('#' + id).find('#cs-pagination-last-flag').unbind('click').click(function () {
                    if($(this).hasClass('cs-pagination-last')){
                        var pageNo = $(this).attr('data-index');
                        clickFn(pageNo,pageSize);
                    }else{
                        var inputNo = $(this).siblings().filter('input').val();
                        if (inputNo < 1) {
                            inputNo = 1;
                        }
                        if (/(^[1-9]\d*$)/.test(inputNo)) {
                            if (inputNo > pageCount){
                                inputNo = pageCount;
                            }
                            var  pageNo=inputNo;
                            clickFn(pageNo,pageSize);
                        }else{
                            return;
                        }
                    }
                })
                if(showPageSizeFlag == true){
                    $('#' + id).find('#pageSize15').unbind('click').click(function () {
                        pageSize = 15;
                        clickPageSize(pageSize);
                    })
                    $('#' + id).find('#pageSize50').unbind('click').click(function () {
                        pageSize = 50;
                        clickPageSize(pageSize);
                    })
                    $('#' + id).find('#pageSize100').unbind('click').click(function () {
                        pageSize = 100;
                        clickPageSize(pageSize);
                    })
                    $('#' + id).find('#pageSize200').unbind('click').click(function () {
                        pageSize =200;
                        clickPageSize(pageSize);
                    })
                    $('#' + id).find('#pageSize500').unbind('click').click(function () {
                        pageSize = 500;
                        clickPageSize(pageSize);
                    })
                }
                if(pageSize == 15){
                    $('#' + id).find("#pageSize15").css('color','#FFFFFF').css('background','#00B69F');
                }
                if(pageSize == 50){
                    $('#' + id).find("#pageSize50").css('color','#FFFFFF').css('background','#00B69F');
                }
                if(pageSize == 100){
                    $('#' + id).find("#pageSize100").css('color','#FFFFFF').css('background','#00B69F');
                }
                if(pageSize == 200){
                    $('#' + id).find("#pageSize200").css('color','#FFFFFF').css('background','#00B69F');
                }
                if(pageSize == 500){
                    $('#' + id).find("#pageSize500").css('color','#FFFFFF').css('background','#00B69F');
                }

                var clickPageSize = function(pageSize){
                    if($(this).hasClass('cs-pagination-last')){
                        var pageNo = $(this).attr('data-index');
                        clickFn(pageNo,pageSize);
                    }else{
                        clickFn(1,pageSize);
                    }
                }

            }
            return pagination;
        }
    })

    // 弹框提示
    $provide.provider('box', function () {
        this.$get = function () {
            var box = {};

            // loading
            box.loading = function () {
                if (document.getElementById('loading') == null) {
                    angular.element(document.body).append(
                        '<div class="cs-mask cs-mask-loading" id="loading">' +
                        '<div class="spinner">' +
                        '<div class="spinner-container container1">' +
                        '<div class="circle1"></div>' +
                        '<div class="circle2"></div>' +
                        '<div class="circle3"></div>' +
                        '<div class="circle4"></div>' +
                        '</div>' +
                        '<div class="spinner-container container2">' +
                        '<div class="circle1"></div>' +
                        '<div class="circle2"></div>' +
                        '<div class="circle3"></div>' +
                        '<div class="circle4"></div>' +
                        '</div>' +
                        '<div class="spinner-container container3">' +
                        '<div class="circle1"></div>' +
                        '<div class="circle2"></div>' +
                        '<div class="circle3"></div>' +
                        '<div class="circle4"></div>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    angular.element(document.getElementById('loading')).css('display', 'block');
                }
            }
            box.removeLoading = function () {
                if (document.getElementById('loading') != null) {
                    angular.element(document.getElementById('loading')).css('display', 'none');
                }
            }

            // table loading
            box.tableLoading = function (id) {
                $('#' + id).css('position', 'relative');
                if ($('#' + id).find('.table_loaging').length == 0) {
                    $('#' + id).append(
                        '<div class="cs-mask cs-mask-loading table_loaging">' +
                        '<div class="spinner">' +
                        '<div class="spinner-container container1">' +
                        '<div class="circle1"></div>' +
                        '<div class="circle2"></div>' +
                        '<div class="circle3"></div>' +
                        '<div class="circle4"></div>' +
                        '</div>' +
                        '<div class="spinner-container container2">' +
                        '<div class="circle1"></div>' +
                        '<div class="circle2"></div>' +
                        '<div class="circle3"></div>' +
                        '<div class="circle4"></div>' +
                        '</div>' +
                        '<div class="spinner-container container3">' +
                        '<div class="circle1"></div>' +
                        '<div class="circle2"></div>' +
                        '<div class="circle3"></div>' +
                        '<div class="circle4"></div>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    $('#' + id).find('.table_loaging').show();
                }
            }
            box.removeTableLoading = function (id) {
                if ($('#' + id).find('.table_loaging').length != 0) {
                    $('#' + id).find('.table_loaging').hide();
                }
            }

            // 提示框
            box.alert = function (className, textContent, width) {
                if (document.getElementById(className) == null) {
                    var styleStr = '';
                    if (!!width) {
                        styleStr = ' style="width: ' + width + 'px;"';
                    }
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content" id="' + className + '-content"' + styleStr + '>' +
                        '<span id="' + className + '-text">' + textContent + '</span>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                    angular.element(document.getElementById(className)).bind('click', function (event) {
                        angular.element(this).css('display', 'none');
                    })
                    angular.element(document.getElementById(className + '-content')).bind('click', function (event) {
                        event.stopPropagation();
                    })
                } else {
                    if (!!width) {
                        angular.element(document.getElementById(className + '-content')).css('width', width + 'px');
                    } else {
                        angular.element(document.getElementById(className + '-content')).removeAttr('style');
                    }
                    angular.element(document.getElementById(className + '-text')).html(textContent);
                    angular.element(document.getElementById(className)).css('display', 'block');
                }
            }
            // 提示框
            box.alertError = function (className, textContent) {
                if (document.getElementById(className) == null) {
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content" id="' + className + '-content">' +
                        '<span id="' + className + '-text">' + textContent + '</span>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                    angular.element(document.getElementById(className)).bind('click', function (event) {
                        angular.element(this).css('display', 'none');
                    })
                    angular.element(document.getElementById(className + '-content')).bind('click', function (event) {
                        event.stopPropagation();
                    })
                } else {
                    angular.element(document.getElementById(className + '-text')).html(textContent);
                    angular.element(document.getElementById(className)).css('display', 'block');
                }
            }
            // 确认框
            box.confirm = function () {

            }
            // 上传界面
            box.openUpload = function (className, isShowUploadBtn, chooseFileFn) {
                if (document.getElementById(className) == null) {
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<h3>图片上传</h3>' +
                        '<input type="text" placeholder="文件名" id="uploadPicName" />' +
                        '<input type="hidden" id="uploadPicUrl" />' +
                        '<span id="chooseFile" class="cs-model-button">选择文件</span>' +
                        '<span id="realUpload" class="cs-model-button"' + (isShowUploadBtn ? '' : ' style="display: none;"') + '>上传</span>' +
                        '<div contenteditable="true" id="showUploadPicArea"></div>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    if (isShowUploadBtn) {
                        $('#realUpload').show();
                    } else {
                        $('#realUpload').hide();
                    }
                    fatherObj.find('input').val('');
                    $('#showUploadPicArea').empty();
                    fatherObj.css('display', 'block');
                }

                $('#chooseFile').unbind('click').click(function () {
                    chooseFile(chooseFileFn);
                })

                var mb = myBrowser();
                if ("Safari" == mb || "Opera" == mb) {// Safari/Opera/IE11以下禁止复制粘贴上传，控件展示在Safari下不支持
                    $('#showUploadPicArea').removeAttr('contenteditable');
                } else {
                    new UploadImage("showUploadPicArea", '/apollo/csplatform/uploadFile/preUpload').upload(function (xhr) {//上传完成后的回调
                        var resultData = JSON.parse(xhr.response);
                        if (resultData.code == 0) {
                            if ("Chrome" == mb) {// Chrome下不会复制图片，主动创建图片；火狐和Safari下会自动复制
                                var img = new Image();
                                img.src = resultData.detailInfo.url;
                                this.appendChild(img);
                            }
                            $('#uploadPicUrl').val(resultData.detailInfo.url);
                            $('#uploadPicName').val(resultData.detailInfo.srcName.split('.')[0]);
                        } else {
                            if ("FF" == mb) {// 火狐下上传失败删除自动生成的图片
                                this.empty();
                            }
                        }
                        if (chooseFileFn) {
                            chooseFileFn(resultData);
                        }
                    });
                }
            }
            // 图片列表查看
            box.openPicList = function (className, list) {
                if (document.getElementById(className) == null) {
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<img onclick="openBigPic(this)" src="' + list[i].url + '" />';
                    }
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<h3>附件</h3>' +
                        '<div class="cs-mask-picList-content clearfix" id="' + className + '-list-content">' +
                        str +
                        '</div>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<img onclick="openBigPic(this)" src="' + list[i].url + '" />';
                    }
                    $('#' + className + '-list-content').empty().append(str);
                    fatherObj.css('display', 'block');
                }
            }
            // 手机号码列表
            box.openPhoneList = function (className, list) {
                if (document.getElementById(className) == null) {
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<tr><td>' + list[i].name + '</td>'
                            + '<td><span class="text_click callPhoneTool" onclick="autoCallPhone(\'' + list[i].phone + '\', event)" onmouseover="phoneOver(this)" onmouseout="phoneOut(this)">' + list[i].phone
                            + '<div class="callPhoneTool-content"><i class="callPhoneTool-iconTel" onclick="autoCallPhone(\'' + list[i].phone + '\', event)"></i><i class="callPhoneTool-iconEmail" onclick="autoSendEmail(\'' + list[i].phone + '\', event)"></i></div>'
                            + '</span></td>'
                            + '<td>' + list[i].relation + '</td></tr>';
                    }
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<h3>电话号码</h3>' +
                        '<div class="cs-mask-phoneList-table">' +
                        '<table class="table table-bordered cs-table">' +
                        '<thead><tr><th>姓名</th><th>号码</th><th>关系</th></tr></thead>' +
                        '<tbody id="' + className + '-tbody">' + str + '</tbody>' +
                        '</table>' +
                        '</div>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<tr><td>' + list[i].name + '</td><td>' + list[i].phone + '</td><td>' + list[i].relation + '</td></tr>';
                    }
                    $('#' + className + '-body').empty().append(str);
                    fatherObj.css('display', 'block');
                }
            }
            // 公告弹框
            box.showNotice = function (className, list) {
                if (document.getElementById(className) == null) {
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<li>' + list[i] + '</li>';
                    }
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<h3>公告</h3>' +
                        '<ul class="cs-mask-notice-content" id="' + className + '-body">' +
                        str +
                        '</ul>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<li>' + list[i] + '</li>';
                    }
                    $('#' + className + '-body').empty().append(str);
                    fatherObj.css('display', 'block');
                }
            }
            // 甲方案件注记展示
            box.openCaseRecordList = function (className, list) {
                if (document.getElementById(className) == null) {
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<li><span style="margin-right: 10px;">' + (i + 1) + '.</span>' + list[i].remark + '</li>';
                    }
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<h3>案件注记</h3>' +
                        '<ul class="cs-mask-notice-content" id="' + className + '-body">' +
                        str +
                        '</ul>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    var str = '';
                    for (var i = 0; i < list.length; i++) {
                        str += '<li><span style="margin-right: 10px;">' + (i + 1) + '.</span>' + list[i].remark + '</li>';
                    }
                    $('#' + className + '-body').empty().append(str);
                    fatherObj.css('display', 'block');
                }
            }
            // 待办事宜详情弹框
            box.showTodoDialog = function (className, data) {
                if (document.getElementById(className) == null) {
                    var str = '<li class="clearfix"><span class="cs-mask-todo-infoTitle">案件编号</span><span>' + data.caseId + '</span></li>' +
                        '<li class="clearfix"><span class="cs-mask-todo-infoTitle">罚息减免金额</span><span>' + data.reductionAmt + '元</span></li>' +
                        '<li class="clearfix"><span class="cs-mask-todo-infoTitle">罚息减免原因</span><span>' + data.reductionReason + '</span></li>' +
                        '<li class="clearfix"><span class="cs-mask-todo-infoTitle">附件图片</span><span><img src="' + data.attachmentUrl + '" onclick="openBigPic(this)" /></span></li>';
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix">' +
                        '<h3>罚息减免</h3>' +
                        '<ul id="' + className + '-content">' +
                        str +
                        '</ul>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    var str = '<li class="clearfix"><span class="cs-mask-todo-infoTitle">案件编号</span><span>' + data.caseId + '</span></li>' +
                        '<li class="clearfix"><span class="cs-mask-todo-infoTitle">罚息减免金额</span><span>' + data.reductionAmt + '元</span></li>' +
                        '<li class="clearfix"><span class="cs-mask-todo-infoTitle">罚息减免原因</span><span>' + data.reductionReason + '</span></li>' +
                        '<li class="clearfix"><span class="cs-mask-todo-infoTitle">附件图片</span><span><img src="' + data.attachmentUrl + '" onclick="openBigPic(this)" /></span></li>';
                    $('#' + className + '-content').empty().append(str);
                    fatherObj.css('display', 'block');
                }
            }
            // 修改密码
            box.openPasswordDialog = function (className, validate, box) {
                window.g_validate = validate;
                window.g_box = box;
                if (document.getElementById(className) == null) {
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<div class="cs-mask-password-tool clearfix">' +
                        '<span>旧密码：</span><input type="password" id="oldPsd" />' +
                        '</div>' +
                        '<div class="cs-mask-password-tool clearfix">' +
                        '<span>新密码：</span><input type="password" id="newPsd" />' +
                        '</div>' +
                        '<div class="cs-mask-password-tool clearfix">' +
                        '<span>确认密码：</span><input type="password" id="rePsd" />' +
                        '</div>' +
                        '<div class="cs-model-button" onclick="updatePassword()">修改</div>' +
                        '<i class="cs-mask-close" onclick="closeMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    );
                } else {
                    var fatherObj = angular.element(document.getElementById(className));
                    fatherObj.css('display', 'block');
                }
            }
            // 播放界面
            box.openPlayerDialog = function (className, item, ajax, $http, box) {
                ajax.http($http, box, '/dial/playRecord?callId=' + item.voiceToken, '', function (data) {
                    angular.element(document.body).append(
                        '<div class="cs-mask ' + className + '" id="' + className + '" style="display: none;">' +
                        '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                        '<h3>播放录音</h3>' +
                        '<div class="cs-mask-player-video">' +
                        '<audio id="audio" src="' + data.voiceUrl + '" preload="auto" controls=""></audio>' +
                        '</div>' +
                        '<i class="cs-mask-close" onclick="closePlayerMask(\'' + className + '\')"></i>' +
                        '</div>' +
                        '</div>'
                    )
                    $('#audio').audioPlayer();
                    $('#' + className).show();
                }, 'get')
            }
            //播放录音界面
            box.openPlayerRecord = function (className, voiceUrl) {
                angular.element(document.body).append(
                    '<div class="cs-mask ' + className + '" id="' + className + '" style="display: none;">' +
                    '<div class="cs-mask-content clearfix" id="' + className + '-content">' +
                    '<h3>播放录音</h3>' +
                    '<div class="cs-mask-player-video">' +
                    '<audio id="audio" src="' + voiceUrl + '" preload="auto" controls=""></audio>' +
                    '</div>' +
                    '<i class="cs-mask-close" id="cs-mask-close"></i>' +
                    '</div>' +
                    '</div>'
                );
                $('#audio').audioPlayer();
                $('#' + className).show();
            };

            return box;
        }
    })
})

// 关闭遮罩层
function closeMask(id) {
    angular.element(document.getElementById(id)).css('display', 'none');
}

// 关闭播放录音遮罩层
function closePlayerMask(id) {
    $('#' + id).remove();
}

// 图片上传-选择文件上传
function chooseFile(chooseFileFn) {
    $.upload({
        // 上传地址
        url: '/apollo/csplatform/uploadFile/preUpload',
        // 文件域名字
        fileName: 'AreaImgKey',
        // 上传完成后, 返回json, text
        dataType: 'json',
        // 上传之前回调,return true表示可继续上传
        onSend: function () {
            var imgArr = $('#showUploadPicArea').find('img');
            if (imgArr.length > 0) {
                var mb = myBrowser();
                if ("Chrome" != mb) {
                    imgArr[1].remove();
                }
                return false;
            }
            return true;
        },
        // 上传之后回调
        onComplate: function (data) {
            if (chooseFileFn) {
                chooseFileFn(data);
            }
            if (data.code != 0) {
                return;
            }
            var img = new Image();
            img.src = data.detailInfo.url;
            document.getElementById('showUploadPicArea').appendChild(img);
            $('#uploadPicName').val(data.detailInfo.srcName.split('.')[0]);
            $('#uploadPicUrl').val(data.detailInfo.url);
        }
    });
}

// 判断当前浏览器
function myBrowser() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }
    ; //判断是否Opera浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("Chrome") > -1) {
        return "Chrome";
    }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //判断是否Safari浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }
    ; //判断是否IE浏览器
}

// 图片放大
function openBigPic(self) {
    var url = $(self).attr('src');
    if (document.getElementById('cs-mask-bigPic') == null) {
        $('body').append(
            '<div class="cs-mask cs-mask-bigPic" id="cs-mask-bigPic">' +
            '<div class="cs-mask-bigPic-content" id="cs-mask-bigPic-img">' +
            '<img />' +
            '<i class="cs-mask-rotate" onclick="rotatePic()"></i>' +
            '</div>' +
            '<i class="cs-mask-close" onclick="closeMask(\'cs-mask-bigPic\')"></i>' +
            '</div>'
        );
    }
    getImageWidth(url, function (w, h) {
        $('#cs-mask-bigPic-img').width(w).height(h);
        $('#cs-mask-bigPic-img').find('img').attr('src', url)
        $('#cs-mask-bigPic').show();
        $('#cs-mask-bigPic').unbind('click').bind('click', function (event) {
            closeMask('cs-mask-bigPic');
        })
        $('#cs-mask-bigPic-img').unbind('click').bind('click', function (event) {
            event.stopPropagation();
        })
    })
}

function getImageWidth(url, callback) {
    var img = new Image();
    img.src = url;
    // 如果图片被缓存，则直接返回缓存数据
    if (img.complete) {
        callback(img.width, img.height);
    } else {
        // 完全加载完毕的事件
        img.onload = function () {
            callback(img.width, img.height);
        }
        img.onerror = function () {
            callback(20, 20);
        }
    }
}

function rotatePic() {
    if ($('#cs-mask-bigPic-img').find('img').hasClass('cs-mask-bigPic-rotate')) {
        $('#cs-mask-bigPic-img').find('img').removeClass('cs-mask-bigPic-rotate');
    } else {
        $('#cs-mask-bigPic-img').find('img').addClass('cs-mask-bigPic-rotate');
    }
}

// 修改密码
function updatePassword() {
    var oldPassword = $.trim($('#oldPsd').val()), newPassword = $.trim($('#newPsd').val()), confirmPassword = $.trim($('#rePsd').val());
    var r_op = g_validate.isPassword(oldPassword), r_np = g_validate.isPassword(newPassword), r_rp = g_validate.isPassword(confirmPassword);
    if (r_op != 0) {
        g_box.alert('cs-mask-text', '输入的旧密码格式有误！');
        return;
    }
    if (r_np != 0) {
        g_box.alert('cs-mask-text', '输入的新密码格式有误！');
        return;
    }
    if (r_rp != 0) {
        g_box.alert('cs-mask-text', '再次输入的新密码格式有误！');
        return;
    }
    if (oldPassword == newPassword) {
        g_box.alert('cs-mask-text', '新旧密码不能相同！');
        return;
    }
    if (newPassword != confirmPassword) {
        g_box.alert('cs-mask-text', '2次输入的新密码不相同！');
        return;
    }
    $.ajax({
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: '/apollo/csplatform/user/modifyPassword',
        async: true,
        crossDomain: true,
        data: JSON.stringify({
            oldPassword: sha512_256(oldPassword),
            newPassword: sha512_256(newPassword),
            confirmPassword: sha512_256(confirmPassword)
        }),
        success: function (data) {
            if (data.code == 0) {
                $('#oldPsd').val('');
                $('#newPsd').val('');
                $('#rePsd').val('');
                closeMask('cs-mask-password');
            } else if (data.code == 9009 || data.code == 9023) {
                g_box.alert('cs-mask-text', data.desc);
                window.location.href = '/apollo/csplatform/newViews/login.html';
            } else {
                g_box.alert('cs-mask-text', data.desc);
            }
        }
    });
}

function phoneOver(obj) {
    $(obj).find('.callPhoneTool-content').css('visibility', 'visible');
}

function phoneOut(obj) {
    $(obj).find('.callPhoneTool-content').css('visibility', 'hidden');
}

function autoCallPhone(phone, event) {
    $('#telPhoneNumber').val(phone);
    $('.wcCaseInfo-telTool-content .tab-nav span').eq(0).click();
    $('#autoCallPhone').click();
    event.stopPropagation();
}

function autoSendEmail(phone, event) {
    $('#smsPhoneNumber').val(phone);
    $('.wcCaseInfo-telTool-content .tab-nav span').eq(1).click();
    event.stopPropagation();
}
var constructDeptTree_2 = function (treeNode, serverData) {
    serverData.forEach(function (value) {
        var node = {};
        node.deptId = value.deptId;
        node.text = value.deptName;
        node.selectable = false;
        node.state = {};
        node.state.expanded = false;
        treeNode.push(node);
        if (value.sonList != null && value.sonList.length > 0) {
            node.nodes = [];
            constructDeptTree_2(node.nodes, value.sonList)
        }
    })
}

var constructDeptTree_3 = function (node,serverData) {
    serverData.forEach(function (value) {
        if(null == value.nodes
            ||  value.nodes.length==0){
            value.selectable = true;
            if(null != node){
                node.selectable = true;
            }
        }else{
            constructDeptTree_3(value,value.nodes)
        }
    })
}



var constructDeptTree = function (treeNode, serverData, depUserMap, userList, deptIdList) {
    serverData.forEach(function (value) {
        var node = {};
        node.deptId = value.deptId
        node.text = value.deptName;
        node.selectable = false;
        node.state = {};
        if (deptIdList != null && deptIdList.length > 0){
            deptIdList.forEach(function (deptId) {
                if (value.deptId == deptId){
                    node.state.checked = true;
                }
            })
        }
        node.state.expanded = false;
        treeNode.push(node);
        if (value.userEntities != null && value.userEntities.length != 0) {
            var key = value.deptId;
            depUserMap[key] = value.userEntities;
            value.userEntities.forEach(function (user) {
                userList.push(user);
            })
        }
        if (value.sonList != null && value.sonList.length > 0) {
            node.nodes = [];
            constructDeptTree(node.nodes, value.sonList, depUserMap, userList, deptIdList);
        }
    })
}

var nodeCheckedSilent = false;
var nodeUncheckedSilent = false;

var nodeChecked = function (event, node) {
    if (nodeCheckedSilent) {
        return;
    }
    var tree = $(event.target).closest('.treeview');
    nodeCheckedSilent = true;
    // checkAllParent(node);
    checkAllSon(node, tree);
    nodeCheckedSilent = false;
}
var nodeUnChecked = function (event, node) {
    if (nodeUncheckedSilent)
        return;
    nodeUncheckedSilent = true;
    //取消选中子部门时暂时不需要取消选中父部们
    // uncheckAllParent(node);
    var tree = $(event.target).closest('.treeview');
    uncheckAllSon(node, tree);
    nodeUncheckedSilent = false;
}
var nodeUnCheckedAll = function (event, node) {
    if (nodeUncheckedSilent)
        return;
    nodeUncheckedSilent = true;
    var tree = $(event.target).closest('.treeview');
    uncheckAllSon(node, tree);
    //取消选中子部门时需要取消选中父部们
    uncheckAllParent(node, tree);
    nodeUncheckedSilent = false;
}

//选中全部父节点
function checkAllParent(node, tree) {
    tree.treeview('checkNode', [node.nodeId, {silent: true}]);
    var parentNode = tree.treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    } else {
        checkAllParent(parentNode, tree);
    }
}

//取消全部父节点
function uncheckAllParent(node, tree) {
    tree.treeview('uncheckNode', [node.nodeId, {silent: true}]);
    var siblings = tree.treeview('getSiblings', node.nodeId);
    var parentNode = tree.treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    }
    // var isAllUnchecked = true;  //是否全部没选中
    for (var i = 0; i < siblings.length; i += 1) {
        if (siblings[i].state.checked) {
            // isAllUnchecked = false;
            break;
        }
    }
    // if (isAllUnchecked) {
        uncheckAllParent(parentNode, tree);
    // }
}

//级联选中所有子节点
function checkAllSon(node, tree) {
    tree.treeview('checkNode', [node.nodeId, {silent: true}]);
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i = 0; i < node.nodes.length; i += 1) {
            checkAllSon(node.nodes[i], tree);
        }
    }
}

//级联取消所有子节点
function uncheckAllSon(node, tree) {
    tree.treeview('uncheckNode', [node.nodeId, {silent: true}]);
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i = 0; i < node.nodes.length; i += 1) {
            uncheckAllSon(node.nodes[i], tree);
        }
    }
}

