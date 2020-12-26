module com.success.four {
	 // no need to explicity declare one here because one will be imported thru three.
//	requires com.success.one;
	
	requires com.success.three;
	
	//this is required to use ServiceLoader to load IOne implementation
	uses com.success.one.base.services.IOne;
}