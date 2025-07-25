package ee.bcs.cinemaback.service.user;

import ee.bcs.cinemaback.persistence.user.User;
import ee.bcs.cinemaback.service.user.dto.LoginResponse;
import ee.bcs.cinemaback.service.user.dto.RegistrationRequest;
import ee.bcs.cinemaback.service.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    @Mapping(source = "username", target = "username")
    @Mapping(ignore = true, target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(ignore = true, target = "role.name")
    User toUser(RegistrationRequest registrationRequest);
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(ignore = true, target = "token")
    LoginResponse toLoginResponse(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "status", target = "status")
    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

}
