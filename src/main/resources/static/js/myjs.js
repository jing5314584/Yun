//uedit 的工具tools
var tools= [[
            'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', 
            '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase',  
             '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage',  'map', '|',
            'horizontal', '|', 'print', 'preview', 'searchreplace'
        ]];

//定义全局数组
var imagLoadPackageArr=[];

var imagLoadSrc=[];
//判断左侧隐藏按钮是否显示
     $(function(){
     	imagLoadPackage();
     });
     //弹出alert
     function my_alert(msg)
     {
    	 $("#my-alert-overall-msg").html(msg);
    	 $('#my-alert-overall').modal('open');
 		 $('#foam-zcc').show();
     }
     
     //关闭alert
     function closeOverallZzc()
     {
    	 $("#foam-zcc").hide();
     }
     
     
     function wxPreview(src){
//    	 alert(src);
//    	 alert(imagLoadSrc);
    	 if(imagLoadSrc.length > 0)
    	 {
    		 wx.previewImage({
     		    current: src, // 当前显示图片的http链接
     			urls:imagLoadSrc
     	 	});
    	 }else{
    		 wx.previewImage({
      		    current: src, // 当前显示图片的http链接
      			urls:[src]
      	 	});
    	 }
     }
     
     function imagLoadPackage(){
	    //下面是获取当前页面所有的img的src 
	    var i=0;
	    var json=null;
	    imagLoadPackageArr=$('img.previewImag_total');
	    if(imagLoadPackageArr.length > 0)
	    {
	    	for (i=0;i<imagLoadPackageArr.length;i++){
		    		imagLoadSrc[i]=imagLoadPackageArr[i].src;    //把所有的src存到数组里
		    }
	    }
	}
     
     $(function(){
    		var $winwidth=$("body").width();
    		if($winwidth > 414){
    			$(".footeNavbar").css({
    				"width":"640px",
    				"left":"50%",
    				"margin-left":"-320px"
    			});
    			$(".foamModal").css({
					"width":"400px",
					"margin-left":"-200px"
				})
    		}
    	});
