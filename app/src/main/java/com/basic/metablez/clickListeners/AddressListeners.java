package com.basic.metablez.clickListeners;

import com.basic.metablez.models.AddressModel;

public interface AddressListeners {

    void updateAddress(int position, AddressModel addressModel);

    void deleteAddress(int position);
}
