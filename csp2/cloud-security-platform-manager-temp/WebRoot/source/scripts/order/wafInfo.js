window.onload = function () {
    initEvent();
}

function initEvent() {
    const select = document.getElementById("select");
    const add = document.getElementById("add");
    select.onchange = changeSelect;
    add.onclick = clickAdd;
}

function changeSelect(e) {
    const dateInput = document.getElementById("dateGroup");
    const securityLevelInfo = document.getElementById("securityLevel");
    const securityTypeInfo = document.getElementById("securityType");
    const securityTimeInfo = document.getElementById("securityTime");
    const attackNumInfo = document.getElementById("attackNum");
    const wafBaseSummaryInfo = document.getElementById("wafBaseSummary");
    
    if(e.target.value == 'week'){
    		wafBaseSummaryInfo.setAttribute("class", "show");
    		$.ajax({
    			type : "POST",
			url : ""
    		});
    }
    
    
    if(e.target.value == 'custom') {
        dateInput.setAttribute("class", "show");
    }
    else {
        dateInput.setAttribute("class", "hide");
    }
}

function clickAdd(e) {
    const dateStart = document.getElementById("dateStart");
    const dateEnd = document.getElementById("dateEnd");
    //value of date input
    if (!dateStart.value || !dateEnd.value) {
        alert("请选择日期");
    }
    if(dateStart.value > dateEnd.value) {
        alert("结束日期需晚于开始日期");
    }
}