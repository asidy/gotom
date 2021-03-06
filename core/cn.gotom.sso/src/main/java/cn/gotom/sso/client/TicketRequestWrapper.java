package cn.gotom.sso.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import cn.gotom.sso.Ticket;

public class TicketRequestWrapper extends HttpServletRequestWrapper
{

	private final Ticket ticket;

	public TicketRequestWrapper(HttpServletRequest request, Ticket ticket)
	{
		super(request);
		this.ticket = ticket;
	}

	public Ticket getTicket()
	{
		return ticket;
	}

	@Override
	public String getRemoteUser()
	{
		return ticket != null ? this.ticket.getUser() : null;
	}
}
