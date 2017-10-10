package com.success.ndb.assemblers;

import java.util.ArrayList;
import java.util.List;

import com.success.ndb.dto.StateDTO;
import com.success.ndb.entities.State;

public class StateAssembler {

	public static StateDTO assemble(State state, boolean assembleCountry) {
		if(state==null){
			throw new RuntimeException("State Not Found.");
		}
		StateDTO dto = new StateDTO();
		dto.setCode(state.getCode());
		dto.setName(state.getName());
		dto.setId(state.getId());
		if (assembleCountry && state.getCountry() != null) {
			dto.setCountry(CountryAssembler.assemble(state.getCountry(), false));
		}
		return dto;
	}

	public static List<StateDTO> assemble(List<State> states, boolean assembleCountry) {
		List<StateDTO> list = new ArrayList<>();
		for (State state : states) {
			list.add(assemble(state, assembleCountry));
		}
		return list;
	}

	public static List<StateDTO> assemble(List<State> states) {
		return assemble(states, true);
	}

	public static StateDTO assemble(State state) {
		return assemble(state, true);
	}
}