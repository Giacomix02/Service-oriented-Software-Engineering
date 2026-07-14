"use client"
import { useEffect, useState } from "react";
import { TicketingEvent } from "@/types";
import { getAllTicketingEvents, searchTicketingEventsByName } from "@/repository/TicketingRepository";
import EventCard from "@/components/Cards/EventCard/EventCard";
import styles from "../ticketing.module.css";

export default function Page() {
    const [events, setEvents] = useState<TicketingEvent[]>([]);
    const [query, setQuery] = useState("");

    useEffect(() => {
        if (query.trim() === "") {
            getAllTicketingEvents(setEvents);
        } else {
            searchTicketingEventsByName(query, setEvents);
        }
    }, [query]);

    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Events</h1>

            <div className={styles.searchRow}>
                <input
                    type="text"
                    placeholder="Search event by name."
                    className={styles.searchInput}
                    onChange={(event) => setQuery(event.target.value)}
                />
            </div>

            <div className={styles.cardsGrid}>
                {events.length === 0
                    ? <p className={styles.empty}>No events found.</p>
                    : events.map((event) => <EventCard key={event.eventGlobalId} event={event} />)
                }
            </div>
        </div>
    )
}