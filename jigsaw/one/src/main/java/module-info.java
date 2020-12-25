module com.success.one {
	exports com.success.one.base to com.success.two;
	
	opens com.success.one.base to com.success.two;
	
	exports com.success.one.base.services;
//	provides com.success.one.base.services.IOne with com.success.one.base.services.OneImpl;
}