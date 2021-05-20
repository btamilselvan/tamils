module com.success.one {
	exports com.success.one.base to com.success.two;
	
	opens com.success.one.base to com.success.two;
	
	exports com.success.one.base.services;
	//exports the implementation to other packages
	provides com.success.one.base.services.IOne with com.success.one.base.services.OneImpl;
}