package com.epam.jmp.troubleshooting;

class Key {

	Integer id;

	Key(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
