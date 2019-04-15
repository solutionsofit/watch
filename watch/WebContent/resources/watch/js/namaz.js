function digitalWatch() {
 	var setn = "set";
	var massiv="get";	
	 var p_1 = '#{mnamaz.param_1}';
	 var ot = document.getElementById("test").innerHTML=p_1;
    var date = new Date();
    var array=["Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Понедельник"];

    var todayD=date.getDay();
    var todayDD=date.getDate();
  
    var todayM=date.getMonth()+1;
    var todayMM=date.getMonth();
    var todayY=date.getFullYear();
    var today=new Date();

	var massiv3;
	
	massiv3=massiv.slice(1).split(','+' ',6)
	
	var namazHrArray;
	console.log(massiv3);
	var namazHrArray1=massiv3[0].charAt(0)+massiv3[0].charAt(1);
	console.log(namazHrArray1);
	var namazHrArray2=massiv3[1].charAt(0)+massiv3[1].charAt(1);
	var namazHrArray3=massiv3[2].charAt(0)+massiv3[2].charAt(1);
	var namazHrArray4=massiv3[3].charAt(0)+massiv3[3].charAt(1);
	var namazHrArray5=massiv3[4].charAt(0)+massiv3[4].charAt(1);
	for(k=0;k<massiv3.length;k++){
	
	var namazHr=massiv3[k].charAt(0)+massiv3[k].charAt(1);
	var namazMin=massiv3[k].charAt(3)+massiv3[k].charAt(4)
	
	var d=new Date();
	var time=d.getHours();
	var min=d.getMinutes();
	
	//Обратый отсчет
		if(time<namazHr){
	var secondTimeend =new Date(todayY,todayMM,todayDD,namazHr,namazMin)
  	today = new Date();
    today = Math.floor(((secondTimeend)-today)/1000);
    tsec=today%60; today=Math.floor(today/60); if(tsec<10)tsec='0'+tsec;
    tmin=today%60; today=Math.floor(today/60); if(tmin<10)tmin='0'+tmin;
    thour=today%24; today=Math.floor(today/24);
    timestr=thour+":"+tmin+":"+tsec;
    
    document.getElementById('t').innerHTML=timestr;
    document.getElementById('t1').innerHTML=timestr;
   		
    	
		break;	
		}
		else if(time==namazHr){
		if(min<namazMin){
		
	var secondTimeend =new Date(todayY,todayMM,todayDD,namazHr,namazMin)
  	today = new Date();
  	
    today = Math.floor(((secondTimeend)-today)/1000);
    tsec=today%60; today=Math.floor(today/60); if(tsec<10)tsec='0'+tsec;
    tmin=today%60; today=Math.floor(today/60); if(tmin<10)tmin='0'+tmin;
    thour=today%24; today=Math.floor(today/24);
    timestr=tmin+":"+tsec;
    document.getElementById('t').innerHTML=timestr;
     document.getElementById('t1').innerHTML=timestr;
  
		break;
		}
		}
		else if((time>namazHr)&&(time>namazHrArray1)&&(time>namazHrArray2)&&(time>namazHrArray3)&&(time>namazHrArray4)&&(time>namazHrArray5)){		
	var secondTimeend =new Date(todayY,todayMM,todayDD+1,namazHr,namazMin)
  	today = new Date(); 
    today = Math.floor(((secondTimeend)-today)/1000);
    tsec=today%60; today=Math.floor(today/60); if(tsec<10)tsec='0'+tsec;
    tmin=today%60; today=Math.floor(today/60); if(tmin<10)tmin='0'+tmin;
    thour=today%24; today=Math.floor(today/24);
    timestr=tmin+":"+tsec;
    document.getElementById('t').innerHTML=timestr;
     document.getElementById('t1').innerHTML=timestr;
   
		break;
		}
	}
   //  Дата -->
 if(todayM <10){
   		today = todayDD+'-'+'0'+todayM +'-'+ todayY;
   	}else{
   		today = todayDD+'-'+todayM +'-'+ todayY;
   		}

	// Часы -->
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (hours < 10) hours = "0" + hours;
    if (minutes < 10) minutes = "0" + minutes;
    if (seconds < 10) seconds = "0" + seconds;
    
    // document.getElementById("digital_watch1").innerHTML = array[todayD]; -->
    document.getElementById("digital_watch").innerHTML = today;
     document.getElementById("digital_watch4").innerHTML = today;
    document.getElementById("digital_watch2").innerHTML = hours + ":" + minutes + ":" + seconds ;
    document.getElementById("digital_watch3").innerHTML = hours + ":" + minutes + ":" + seconds ;

    setTimeout("digitalWatch()", 1000);
  }