module com.success.three {
	// transitive will make sure the modules that depend on "three" will also get packages from "one"
	requires transitive com.success.one;
	
	provides com.success.one.base.services.IOne with com.success.three.services.ServiceImplThree;
}