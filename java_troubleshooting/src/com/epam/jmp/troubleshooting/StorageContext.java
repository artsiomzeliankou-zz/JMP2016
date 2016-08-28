package com.epam.jmp.troubleshooting;

public class StorageContext {

	private ThreadLocal<Storage> sx = new ThreadLocal<Storage>();

    public Storage getSx() {
        return sx.get();
    }
    
    public void setSx(Storage sx) {
        this.sx.set(sx);
    }
}
