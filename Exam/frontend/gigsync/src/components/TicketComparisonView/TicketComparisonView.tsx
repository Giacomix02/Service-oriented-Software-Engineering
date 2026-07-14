import { TicketComparison } from "@/types";
import TicketOfferCard from "@/components/Cards/TicketOfferCard/TicketOfferCard";
import CheapestBanner from "@/components/Cards/CheapestBanner/CheapestBanner";
import styles from "./TicketComparisonView.module.css";

export default function TicketComparisonView(props: { comparison: TicketComparison | undefined }) {
    const comparison = props.comparison;

    if (comparison === undefined) {
        return null;
    }

    const isCheapest = (offer: { id: number, source: string }) =>
        comparison.cheapestOverall !== null &&
        comparison.cheapestOverall.id === offer.id &&
        comparison.cheapestOverall.source === offer.source;

    return (
        <div className={styles.wrapper}>
            <CheapestBanner offer={comparison.cheapestOverall} />

            <div className={styles.columns}>
                <div className={styles.column}>
                    <h2 className={styles.columnTitle}>Official tickets</h2>
                    <div className={styles.columnGrid}>
                        {comparison.officialTickets.length === 0
                            ? <p className={styles.empty}>No official tickets available.</p>
                            : comparison.officialTickets.map((offer) => (
                                <TicketOfferCard
                                    key={`official-${offer.id}`}
                                    offer={offer}
                                    highlight={isCheapest(offer)}
                                />
                            ))
                        }
                    </div>
                </div>

                <div className={styles.column}>
                    <h2 className={styles.columnTitle}>Resale Tickets</h2>
                    <div className={styles.columnGrid}>
                        {comparison.resaleTickets.length === 0
                            ? <p className={styles.empty}>No resale tickets available.</p>
                            : comparison.resaleTickets.map((offer) => (
                                <TicketOfferCard
                                    key={`resale-${offer.id}`}
                                    offer={offer}
                                    highlight={isCheapest(offer)}
                                />
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}