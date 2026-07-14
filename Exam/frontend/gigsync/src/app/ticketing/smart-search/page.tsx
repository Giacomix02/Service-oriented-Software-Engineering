"use client"
import { useState } from "react";
import { TicketComparison, ResellerFilter } from "@/types";
import { searchTicketsByCriteria } from "@/repository/TicketingRepository";
import TicketComparisonView from "@/components/TicketComparisonView/TicketComparisonView";
import styles from "../ticketing.module.css";

export default function Page() {
    const [eventName, setEventName] = useState("");
    const [resellerType, setResellerType] = useState<ResellerFilter>("BOTH");
    const [comparison, setComparison] = useState<TicketComparison | undefined>();
    const [searched, setSearched] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleSearch = async () => {
        if (eventName.trim() === "") return;

        setLoading(true);
        setSearched(false);
        setComparison(undefined);

        await searchTicketsByCriteria(eventName, resellerType, setComparison);

        setSearched(true);
        setLoading(false);
    };

    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Smart Search</h1>

            <div className={styles.smartForm}>
                <input
                    type="text"
                    placeholder="Event name"
                    className={styles.searchInput}
                    value={eventName}
                    onChange={(event) => setEventName(event.target.value)}
                    onKeyDown={(event) => { if (event.key === "Enter") handleSearch(); }}
                />
                <select
                    className={styles.select}
                    value={resellerType}
                    onChange={(event) => setResellerType(event.target.value as ResellerFilter)}
                >
                    <option value="BOTH">Both</option>
                    <option value="OFFICIAL">Official only</option>
                    <option value="RESELLER">Resale only</option>
                </select>
                <button className={styles.searchButton} onClick={handleSearch}>
                    Search
                </button>
            </div>

            {loading && <p className={styles.status}>Searching...</p>}
            {!loading && searched && comparison === undefined && (
                <p className={styles.status}>No event found with that name.</p>
            )}

            <TicketComparisonView comparison={comparison} />
        </div>
    )
}