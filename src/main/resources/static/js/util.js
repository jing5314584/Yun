/**
 * 
 */
function GetUrlParam() {
　　　　var url = document.location.toString();
　　　　var arrObj = url.split("?");
		var arr=[];
　　　　if (arrObj.length > 1) {
　　　　　　var arrPara = arrObj[1].split("&");
　　　　　　
		console.log(arrPara);
　　　　　　for (var i = 0; i < arrPara.length; i++) {
　　　　　　　　/*arr = arrPara[i].split("=");*/
			arr.push(arrPara[i]);
　　　　　　}
　　　　　　return arr;
　　　　}
　　　　else {
　　　　　　return arr;
　　　　}
　　}
function getLocationUrl(){
	var url = self.location.href;
	var arrObj = url.split("?");
	return arrObj[0];
}
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
function popup(mes) {
	var hidvalue_str = mes;
	var divWidth = 260;
	var divHeight = 50;
	var iLeft = ($(window).width() - divWidth) / 2;
	var iTop = ($(window).height() - divHeight) / 2 + $(document).scrollTop();
	var divhtml = $(
			"<div style='background-color: gray;width: 200px;height: 50px;line-height: 50px;margin: 0 auto;text-align: center;border-radius: 6px;color: white' >"
					+ hidvalue_str + "</div>").css({
		position : 'absolute',
		top : iTop + 'px',
		left : iLeft + 'px',
		display : 'none',
		width : divWidth + 'px',
		height : divHeight + 'px'
	});
	divhtml.appendTo('body').fadeIn();
	divhtml.appendTo('body').fadeOut(3000);
}