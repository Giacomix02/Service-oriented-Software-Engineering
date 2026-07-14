"use client"
import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { TicketComparison } from "@/types";
import { getTicketComparison } from "@/repository/TicketingRepository";
import TicketComparisonView from "@/components/TicketComparisonView/TicketComparisonView";
import styles from "../../ticketing.module.css";

export default function Page() {
    const params = useParams<{ eventGlobalId: string }>();
    const eventGlobalId = Number(params.eventGlobalId);

    const [comparison, setComparison] = useState<TicketComparison | undefined>();
    const [loading, setLoading] = useState(true);
    const [notFound, setNotFound] = useState(false);

    useEffect(() => {
        setLoading(true);
        setNotFound(false);
        setComparison(undefined);

        getTicketComparison(eventGlobalId, setComparison)
            .catch(() => setNotFound(true))
            .finally(() => setLoading(false));
    }, [eventGlobalId]);

    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Tickets for event #{eventGlobalId}</h1>

            {loading && <p className={styles.status}>Loading...</p>}
            {!loading && notFound && <p className={styles.status}>Event not found.</p>}
            {!loading && !notFound && <TicketComparisonView comparison={comparison} />}
        </div>
    )
}