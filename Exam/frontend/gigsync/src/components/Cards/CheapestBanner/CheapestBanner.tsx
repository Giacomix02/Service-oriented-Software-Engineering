import styles from './CheapestBanner.module.css';
import cardStyles from '../Card.module.css';
import { TicketOffer } from "@/types";

export default function CheapestBanner(props: { offer: TicketOffer | null | undefined }) {
    const offer = props.offer;

    if (!offer) {
        return (
            <div className={styles.banner}>
                <span className={styles.emptyText}>There are no offers available for this event.</span>
            </div>
        )
    }

    const isOfficial = offer.source === "OFFICIAL";

    return (
        <div className={styles.banner}>
            <span className={styles.label}>Best price</span>
            <span className={styles.price}>{offer.price.toFixed(2)} €</span>
            <span className={styles.seat}>{offer.seat}</span>
            <span className={isOfficial ? cardStyles.badgeOfficial : cardStyles.badgeReseller}>
                {isOfficial ? "Oficial" : "Reventa"}
            </span>
        </div>
    )
}