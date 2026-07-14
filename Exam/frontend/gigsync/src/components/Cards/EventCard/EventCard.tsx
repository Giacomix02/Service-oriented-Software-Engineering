import styles from '../Card.module.css';
import Link from "next/link";
import { TicketingEvent } from "@/types";

export default function EventCard(props: { event: TicketingEvent | undefined }) {
    const event = props.event;
    if (event === undefined || event.eventGlobalId === undefined) return (
        <div className={styles.card}>No event found</div>
    )
    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.title}>{event.name}</div>
                <div className={styles.description}>{event.description}</div>
            </div>
            <div className={styles.meta}>
                <p className={styles.id}>id: {event.eventGlobalId}</p>
                <span className={styles.artist}>{event.artistName}</span>
            </div>
            <div className={styles.footer}>
                <span className={styles.location}>📍 {event.location}</span>
                <Link href={`/ticketing/events/${event.eventGlobalId}`} className={styles.ticketsLink}>
                    See tickets →
                </Link>
            </div>
        </div>
    )
}