package de.koelle.christian.trickytripper.dataaccess.suite.payment;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;

import de.koelle.christian.trickytripper.dataaccess.impl.DataConstants;
import de.koelle.christian.trickytripper.dataaccess.impl.DataManagerImpl;
import de.koelle.christian.trickytripper.dataaccess.suite.util.ModelSetupUtil;
import de.koelle.christian.trickytripper.factories.ModelFactory;
import de.koelle.christian.trickytripper.model.Participant;
import de.koelle.christian.trickytripper.model.Payment;
import de.koelle.christian.trickytripper.model.PaymentCategory;

public class DeleteParticipantInTripTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.deleteDatabase(DataConstants.DATABASE_NAME);
    }

    @Test
    public void testSaveAndLoadPaymentToTrip() {

        DataManagerImpl dataManager = new DataManagerImpl(context);

        dataManager.removeAll();

        long tripId = dataManager.persistTrip(ModelFactory.createNewTrip("MyTrip", Currency.getInstance("USD")))
                .getId();

        Participant p1 = dataManager.persistParticipantInTrip(tripId, ModelFactory.createNewParticipant("Tony", true));
        Participant p2 = dataManager
                .persistParticipantInTrip(tripId, ModelFactory.createNewParticipant("Steve", false));
        Participant p3 = dataManager
                .persistParticipantInTrip(tripId, ModelFactory.createNewParticipant("Bruce", false));

        Participant p4 = dataManager
                .persistParticipantInTrip(tripId, ModelFactory.createNewParticipant("Not part of any payment", false));

        /* Payment 01 */
        Payment payment01In = ModelFactory.createNewPayment("MyDesciption01", PaymentCategory.BEVERAGES);
        ModelSetupUtil.addAmountToPayment(payment01In, 33.20d, "EUR", true, p1);
        ModelSetupUtil.addAmountToPayment(payment01In, 10.10d, "EUR", false, p1);
        ModelSetupUtil.addAmountToPayment(payment01In, 11.10d, "EUR", false, p2);
        ModelSetupUtil.addAmountToPayment(payment01In, 12d, "EUR", false, p3);

        dataManager.persistPaymentInTrip(tripId, payment01In);

        Assert.assertEquals(4, dataManager.loadTripById(tripId).getParticipant().size());
        Assert.assertEquals(true, dataManager.deleteParticipant(p4.getId()));
        Assert.assertEquals(false, dataManager.deleteParticipant(p3.getId()));
        Assert.assertEquals(3, dataManager.loadTripById(tripId).getParticipant().size());

        dataManager.removeAll();
    }

}
