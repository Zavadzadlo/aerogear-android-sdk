package org.aerogear.mobile.security.checks;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

import org.aerogear.mobile.security.DeviceCheckResult;

@RunWith(RobolectricTestRunner.class)
public class EncryptionEnabledCheckTest {

    @Mock
    Context context;

    @Mock
    DevicePolicyManager devicePolicyManager;

    EncryptionEnabledCheck check;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        check = new EncryptionEnabledCheck();
        when(context.getSystemService(context.DEVICE_POLICY_SERVICE))
                        .thenReturn(devicePolicyManager);
    }

    @Test
    public void hasEncryptionEnabled() {
        when(devicePolicyManager.getStorageEncryptionStatus())
                        .thenReturn(DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE);
        DeviceCheckResult result = check.test(context);
        assertTrue(result.passed());
    }

    @Test
    public void hasEncryptionDisabled() {
        when(devicePolicyManager.getStorageEncryptionStatus())
                        .thenReturn(DevicePolicyManager.ENCRYPTION_STATUS_INACTIVE);
        DeviceCheckResult result = check.test(context);
        assertFalse(result.passed());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullContextTest() {
        check.test(null);
    }

}
