var LinkedHashMap = function() {
	this.array = [];
	this.map = {};
}
LinkedHashMap.prototype = {
	constructor : LinkedHashMap,
	/**
	 * 
	 * @param {Object}
	 * @param {Object}i
	 */
	add : function(key, value) {
		var value = value || null;
		var key = key || null;
		var me = this;
		if(key == null) {
			return;
		}
		if( key in me.map) {
			me.map[key] = value;
		} else {
			me.array.push(key);
			me.map[key] = value;
		}
	},
	/**
	 * 删除
	 * @param {Object}
	 */
	remove : function(key) {
		var key = key || null;
		var me = this;
		if( key in me.map) {
			delete me.map[key];
			for(var i = 0; i < me.array.length; i++) {
				if(me.array[i] == key) {
					me.array.splice(i, 1);
					break;
				}
			}
		}
	},

	/**
	 * 
	 * @return {Number}
	 */
	size : function() {
		return this.array.length;
	},
	/**
	 * get
	 * @param {Object}
	 * @return {Object}
	 */
	get : function(key) {
		return this.map[key];
	},
	/**
	 * 
	 * @param {Function}
	 * @return {Object}
	 */
	sort : function(compare) {
		var me = this;
		me.array.sort(compare);
	}
};
