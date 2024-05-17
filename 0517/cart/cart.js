// 숫자 3자리 콤마찍기
Number.prototype.numberFormat = function() {
	if (this == 0)
		return 0;
	let regex = /(^[+-]?\d+)(\d{3})/;
	let nstr = (this + '');
	while (regex.test(nstr)) {
		nstr = nstr.replace(regex, '$1' + ',' + '$2');
	}
	return nstr;
};

let basket = {
	cartCount: 0, // 전체수량.
	cartTotal: 0, // 전체금액.

	list: function() {
		// 목록.
		 
	},

	delItem: function() {
		 
	},

	reCalc: function() {
		//수량, 금액 합계 계산
		//합계 자리에 출력
		 
	},

	changePNum: function(no) { 
  
	},

	delCheckedItem: function() {
		 
	},

	delAllItem: function() {
		 
	},
};

basket.list();
