package cn.gotom.comm.usbhid;

import java.io.IOException;

import cn.gotom.comm.channel.ChannelImpl;
import cn.gotom.comm.channel.State;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

public class HIDChannel extends ChannelImpl
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HIDManager hm;
	private HIDDevice hd;

	static
	{
		ClassPathLibraryLoader.loadNativeHIDLibrary();
		System.out.println("ClassPathLibraryLoader.loadNativeHIDLibrary()");
	}

	@Override
	public String getId()
	{
		return this.parameters.toHidString();
	}

	@Override
	public synchronized void connect() throws IOException
	{
		try
		{
			if (!connected())
			{
				if (hm != null)
				{
					hm.release();
				}
				this.onState(State.Connecting);
				hm = HIDManager.getInstance();
				hd = hm.openById(parameters.getHidVID(), parameters.getHidPID(), null);
				hd.enableBlocking();
				log.debug("连接成功[" + this.getId() + "]SoTimeout=" + parameters.getSoTimeout());
				this.onState(State.Connected);
				super.connect();
			}
		}
		catch (IOException ex)
		{
			this.close();
			this.onState(State.Fail);
			log.error(Thread.currentThread().getName() + "通道[" + getId() + "]连接异常：" + ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public void close()
	{
		try
		{
			if (hm != null)
			{
				hm.release();
			}
			hm = null;
			if (hd != null)
			{
				hd.close();
				log.info("closed[" + this.getId() + "]");
			}
			hd = null;
		}
		catch (Throwable ex)
		{
			log.error(Thread.currentThread().getName() + " close[" + this.getId() + "]", ex);
		}
		super.close();
	}

	@Override
	public boolean connected()
	{
		return hm != null && hd != null;
	}

	@Override
	protected int read(byte[] bytes) throws IOException
	{
		return hd.readTimeout(bytes, this.parameters.getSoTimeout());
	}

	@Override
	public void write(byte[] bytes) throws IOException
	{
		hd.write(bytes);
	}
}