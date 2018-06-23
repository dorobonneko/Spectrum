package com.moe.spectrum;
import android.service.quicksettings.TileService;
import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.content.Context;
import java.lang.reflect.Method;
import android.os.SystemProperties;

public class Switch extends TileService
{
	private int mode;
	@Override
	public void onTileAdded()
	{
		Tile tile=getQsTile();
		if(tile!=null){
			tile.setState(SystemProperties.getInt("spectrum.support",0)==1?Tile.STATE_ACTIVE:Tile.STATE_INACTIVE);
			if(tile.getState()==Tile.STATE_ACTIVE)
			setMode(mode=SystemProperties.getInt("persist.spectrum.profile",0),tile);
			else
				tile.setLabel("内核不支持");
			tile.updateTile();
		}
	}

	@Override
	public void onTileRemoved()
	{
		Tile tile=getQsTile();
		if(tile!=null){
			tile.setState(Tile.STATE_INACTIVE);
			tile.updateTile();
		}
	}

	@Override
	public void onClick()
	{
		mode++;
		if(mode>3)mode=0;
		SystemProperties.set("persist.spectrum.profile",mode+"");
		Tile tile=getQsTile();
		if(tile!=null){
			setMode(mode,tile);
			tile.updateTile();
		}
	}

	@Override
	public void onStartListening()
	{
		onTileAdded();
	}

	
	private void setMode(int mode,Tile tile){
		switch(mode){
			case 0:
				tile.setLabel("均衡");
				break;
			case 1:
				tile.setLabel("激进");
				break;
			case 2:
				tile.setLabel("省电");
				break;
			case 3:
				tile.setLabel("游戏");
				break;
		}
	}
	/*public static Integer getInt(Context context, String key, int def) throws IllegalArgumentException {
        Integer ret = def;
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
				Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            @SuppressWarnings("rawtypes")
				Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;
            Method getInt = SystemProperties.getMethod("getInt", paramTypes);
            Object[] params = new Object[2];
            params[0] = new String(key);
            params[1] = new Integer(def);
            ret = (Integer) getInt.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = def;
        }
        return ret;
    }
	public static void set(Context context, String key, String val) throws IllegalArgumentException {
        try {
           // @SuppressWarnings("unused")
				//DexFile df = new DexFile(new File("/system/app/Settings.apk"));
          //  @SuppressWarnings("unused")
				//ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
				Class SystemProperties = Class.forName("android.os.SystemProperties");
            @SuppressWarnings("rawtypes")
				Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = String.class;
            Method set = SystemProperties.getMethod("set", paramTypes);
            Object[] params = new Object[2];
            params[0] = new String(key);
            params[1] = new String(val);
            set.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
        }
    }*/
}
