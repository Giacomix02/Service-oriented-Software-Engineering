import styles from '../Card.module.css';
import { TicketOffer } from "@/types";

export default function TicketOfferCard(props: { offer: TicketOffer | undefined, highlight?: boolean }) {
    const offer = props.offer;
    if (offer === undefined || offer.id === undefined) return (
        <div className={styles.card}>No ticket found</div>
    )

    const isOfficial = offer.source === "OFFICIAL";

    return (
        <div className={`${styles.card} ${props.highlight ? styles.cheapestCard : ''}`}>
            <div className={styles.header}>
                <div className={styles.title}>{offer.price.toFixed(2)} €</div>
                <div className={styles.description}>{offer.seat}</div>
            </div>
            <div className={styles.meta}>
                <p className={styles.id}>id: {offer.id}</p>
                <span className={isOfficial ? styles.badgeOfficial : styles.badgeReseller}>
                    {isOfficial ? "Oficial" : "Resale"}
                </span>
            </div>
        </div>
    )
}