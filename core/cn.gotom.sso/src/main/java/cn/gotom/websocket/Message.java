package cn.gotom.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

public class Message extends MessageInbound
{
	protected final Logger log = Logger.getLogger(getClass());
	private MessageListener<Message, CharBuffer> messageListener;

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException
	{
		log.debug(arg0.toString());
	}

	/**
	 * 服务器接收到的客户端消息
	 */
	@Override
	protected void onTextMessage(CharBuffer msg) throws IOException
	{
		if (messageListener != null)
		{
			messageListener.onListener(this, msg);
		}
	}

	@Override
	protected void onClose(int status)
	{
		log.debug(this);
		SocketServlet.getMessageInboundList().remove(this);
		super.onClose(status);
	}

	@Override
	protected void onOpen(WsOutbound outbound)
	{
		log.debug(outbound);
		super.onOpen(outbound);
		SocketServlet.getMessageInboundList().add(this);
	}

	public MessageListener<Message, CharBuffer> getMessageListener()
	{
		return messageListener;
	}

	public void setMessageListener(MessageListener<Message, CharBuffer> messageListener)
	{
		this.messageListener = messageListener;
	}

}