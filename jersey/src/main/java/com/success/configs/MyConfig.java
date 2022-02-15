package com.success.configs;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.success.configs.repos.IMyRepository;
import com.success.configs.repos.MyRepository;
import com.success.services.MyService;

public class MyConfig extends ResourceConfig{
	
	public MyConfig() {
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(MyService.class).to(MyService.class);
				bind(MyRepository.class).to(IMyRepository.class);
			}
		});
	}
}
