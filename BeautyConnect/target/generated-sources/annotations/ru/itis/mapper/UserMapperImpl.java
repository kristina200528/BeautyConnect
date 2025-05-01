package ru.itis.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.itis.dictonary.UserStatus;
import ru.itis.dto.RegistrationForm;
import ru.itis.entity.Master;
import ru.itis.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-01T18:59:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToUser(RegistrationForm form, String hashPassword) {
        if ( form == null && hashPassword == null ) {
            return null;
        }

        User user = new User();

        if ( form != null ) {
            user.setRole( form.getRole() );
            user.setUsername( form.getUsername() );
            user.setEmail( form.getEmail() );
            user.setFirstName( form.getFirstName() );
            user.setLastName( form.getLastName() );
            user.setAge( form.getAge() );
        }
        user.setHashPassword( hashPassword );
        user.setStatus( UserStatus.ACTIVE );

        return user;
    }

    @Override
    public Master mapToMaster(RegistrationForm form, User user) {
        if ( form == null && user == null ) {
            return null;
        }

        Master master = new Master();

        if ( form != null ) {
            master.setSpecialization( form.getSpecialization() );
            master.setExperience( form.getExperience() );
            master.setLocation( form.getLocation() );
            master.setContacts( form.getContacts() );
        }
        if ( user != null ) {
            master.setUser( user );
            master.setId( user.getId() );
        }

        return master;
    }
}
