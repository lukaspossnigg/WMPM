package at.ac.wmpm.booking.processor;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.wmpm.booking.model.Ticket;

public class TwitterPost  implements Processor{
	
	private static final Logger log = LoggerFactory.getLogger(TwitterPost.class);

	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Ticket ticket = exchange.getIn().getBody(Ticket.class);
		
		log.debug("TicketDate:"+ ticket.getDate());

		//generate unigue datetime
		Date d = new Date();
		String unitime = ""+d.getTime();
		
		
		String str = ticket.getName() + " has booked a "+ticket.getCategory()+"-Class Trainticket from " + ticket.getFrom() +
		" to " + ticket.getTo() + " on "+ticket.getDate()+" #time"+unitime;
	
		if(str.length() > 140) {
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
            exchange.getOut().setBody("Cannot post on twitter because length of the name oder e-mail adress is to long! (length:"+str.length()+")");
        } else {
			log.debug("Twitterlength:" + str.length());
			exchange.getOut().setBody(str);
        }
		

	}

}



